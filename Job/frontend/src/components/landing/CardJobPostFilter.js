import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import { Pagination } from "@mui/material";
import Stack from "@mui/material/Stack";
import { memo, useEffect, useState } from "react";
import Api, { endpoints } from "../../configs/Api";
import CardItemJobPost from "../cards/CardItemJobPost";
import CardItemJobPostLoading from "../cards/CardItemJobPostLoading";
import CardSearchNoResult from "../commons/CardSearchNoResult";

const CardJobPostFilter = ({
  title = "",
  careerId = "",
  cityId = "",
  experienceId = "",
  salaryId = "",
  positionId = "",
  workingFormId = "",
  isUrgentJob = "",
  pageSize = 10,
}) => {
  const [isLoadingFilterJobPosts, setIsLoadingFilterJobPosts] = useState(true);
  const [filterJobPosts, setFilterJobPosts] = useState([]);
  const [pagination, setPagination] = useState({
    count: 0,
    sizeNumber: 0,
  });
  const [page, setPage] = useState(1);

  // Reset page về 1 mỗi khi filter thay đổi
  useEffect(() => {
    setPage(1);
  }, [
    careerId,
    cityId,
    experienceId,
    salaryId,
    positionId,
    workingFormId,
    isUrgentJob,
    pageSize,
  ]);

  useEffect(() => {
    const loadFilterJobPosts = async () => {
      setIsLoadingFilterJobPosts(true);
      try {
        const query = `career=${careerId}&city=${cityId}&experience=${experienceId}&salary=${salaryId}&position=${positionId}&working_form=${workingFormId}&is_urgent_job=${isUrgentJob}&page_size=${pageSize}&page=${page}`;

        console.log("query: " + query);

        const res = await Api.get(`${endpoints["job-posts"]}?${query}`);
        const data = res.data.data;

        setFilterJobPosts(data.content);
        setPagination({
          count: data.count,
          sizeNumber: Math.ceil(data.count / data.page_size),
        });
      } catch (err) {
        console.error(err);
        setFilterJobPosts([]);
        setPagination({ count: 0, sizeNumber: 0 });
      } finally {
        setIsLoadingFilterJobPosts(false);
      }
    };

    loadFilterJobPosts();
  }, [
    page,
    careerId,
    cityId,
    experienceId,
    salaryId,
    positionId,
    workingFormId,
    isUrgentJob,
    pageSize,
  ]);

  const handleChangePage = (event, value) => {
    setPage(value);
  };

  return (
    <Box>
      {title && (
        <Typography
          component="h4"
          align="center"
          color="text.primary"
          gutterBottom
          sx={{
            marginBottom: 5,
            fontWeight: "bold",
            fontSize: { xs: 24, sm: 30, md: 30 },
          }}
        >
          {title}
        </Typography>
      )}

      <Box>
        <Grid container spacing={2}>
          {isLoadingFilterJobPosts ? (
            Array.from({ length: pageSize }, (_, i) => i + 1).map((value) => (
              <CardItemJobPostLoading key={value} />
            ))
          ) : filterJobPosts.length === 0 ? (
            <CardSearchNoResult description="Không tìm thấy bản tin tuyển dụng nào!" />
          ) : (
            filterJobPosts.map((filterJobPost) => (
              <CardItemJobPost
                key={filterJobPost.id}
                jobPostId={filterJobPost.id}
                avatar={filterJobPost.recruiter.company_cover_image}
                jobName={filterJobPost.job_name}
                companyName={filterJobPost.recruiter.company_name}
                cityName={filterJobPost.city.city_name}
                salaryName={filterJobPost.salary.salary_name}
                deadline={filterJobPost.deadline}
                isUrgentJob={filterJobPost.is_urgent_job}
              />
            ))
          )}
        </Grid>
      </Box>

      {pagination.sizeNumber > 1 && (
        <Box sx={{ pt: 5, pb: 2 }}>
          <Stack>
            <Pagination
              size="large"
              count={pagination.sizeNumber}
              color="primary"
              variant="outlined"
              sx={{ margin: "0 auto" }}
              page={page}
              onChange={handleChangePage}
            />
          </Stack>
        </Box>
      )}
    </Box>
  );
};

export default memo(CardJobPostFilter);
