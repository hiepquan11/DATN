import { useState, memo } from "react";
import {
  Box,
  Button,
  Container,
  Grid,
  MenuItem,
  Paper,
  TextField,
  Typography,
} from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { useForm, Controller } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as Yup from "yup";
import SaveIcon from "@mui/icons-material/Save";
import { useNavigate } from "react-router-dom";

import { authApi, endpoints } from "../configs/Api";
import { updateUser } from "../store/actions/UserCreator";
import { BackdropLoading } from "../components/commons/Loading";
import CardCustomeEditor from "../components/commons/CardCustomEditor";

const schema = Yup.object({
  companyName: Yup.string()
    .required("Tên công ty không được để trống")
    .max(255, "Tên công ty vượt quá độ dài cho phép"),
  fieldOperation: Yup.string()
    .required("Lĩnh vực hoạt động không được để trống")
    .max(255, "Lĩnh vực hoạt động vượt quá độ dài cho phép"),
  companySize: Yup.string()
    .required("Quy mô công ty không được để trống")
    .max(100, "Quy mô công ty vượt quá độ dài cho phép"),
  phoneNumber: Yup.string()
    .required("Số điện thoại không được để trống")
    .max(15, "Số điện thoại vượt quá độ dài cho phép"),
  taxIdNumber: Yup.string().max(15, "Mã số thuế vượt quá độ dài cho phép"),
  companyWebsiteUrl: Yup.string().max(
    255,
    "Địa chỉ website vượt quá độ dài cho phép"
  ),
  address: Yup.string()
    .required("Địa chỉ công ty không được để trống")
    .max(255, "Địa chỉ công ty vượt quá độ dài cho phép"),
  city: Yup.number()
    .required("Bạn phải chọn tỉnh/thành phố")
    .typeError("Bạn phải chọn tỉnh/thành phố"),
});

const RequestRecruiterCompany = () => {
  const dispatch = useDispatch();
  const nav = useNavigate();
  const user = useSelector((state) => state.user);
  const cities = useSelector((state) => state.cities.cities);
  const [companyDescription, setCompanyDescription] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const {
    register,
    handleSubmit,
    control,
    reset,
    setError,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(schema),
    defaultValues: {
      companyName: "",
      fieldOperation: "",
      companySize: "",
      phoneNumber: "",
      taxIdNumber: "",
      companyWebsiteUrl: "",
      address: "",
      city: "",
    },
  });

  const onSubmit = async (data) => {
    setIsLoading(true);

    const formData = new FormData();
    Object.entries(data).forEach(([key, value]) =>
      formData.append(key.replace(/[A-Z]/g, (match) => `_${match.toLowerCase()}`), value)
    );
    formData.append("company_description", companyDescription);

    try {
      const res = await authApi().post(endpoints["company-info"](user.id), formData);
      if (res.status === 200 || res.status === 201) {
        await dispatch(updateUser());
        nav("/recruiter/general-management/");
      }
    } catch (err) {
      if (err.response?.status === 400 && err.response.data?.msg) {
        setError("companyName", {
          type: "manual",
          message: err.response.data.msg,
        });
      }
    } finally {
      setIsLoading(false);
    }
  };

  const onReset = () => {
    reset();
    setCompanyDescription("");
  };

  return (
    <Box>
      <Container maxWidth="md" sx={{ mb: 4 }}>
        <Paper
          variant="outlined"
          sx={{ mt: 6, p: { xs: 2, md: 4 }, boxShadow: 2 }}
        >
          <Typography variant="h4" align="center" sx={{ mb: 3 }} color="info">
            Cập nhật hồ sơ công ty
          </Typography>

          <form onSubmit={handleSubmit(onSubmit)}>
            <Grid container spacing={3}>
              <Grid item xs={12} md={6}>
                <TextField
                  label="Tên công ty (*)"
                  fullWidth
                  size="small"
                  error={!!errors.companyName}
                  helperText={errors.companyName?.message}
                  {...register("companyName")}
                />
              </Grid>

              <Grid item xs={12} md={6}>
                <TextField
                  label="Lĩnh vực hoạt động (*)"
                  fullWidth
                  size="small"
                  error={!!errors.fieldOperation}
                  helperText={errors.fieldOperation?.message}
                  {...register("fieldOperation")}
                />
              </Grid>

              <Grid item xs={12} md={4}>
                <TextField
                  label="Quy mô công ty (*)"
                  fullWidth
                  size="small"
                  error={!!errors.companySize}
                  helperText={errors.companySize?.message}
                  {...register("companySize")}
                />
              </Grid>

              <Grid item xs={12} md={4}>
                <TextField
                  label="Mã số thuế"
                  fullWidth
                  size="small"
                  error={!!errors.taxIdNumber}
                  helperText={errors.taxIdNumber?.message}
                  {...register("taxIdNumber")}
                />
              </Grid>

              <Grid item xs={12} md={4}>
                <TextField
                  label="Điện thoại cố định (*)"
                  fullWidth
                  size="small"
                  error={!!errors.phoneNumber}
                  helperText={errors.phoneNumber?.message}
                  {...register("phoneNumber")}
                />
              </Grid>

              <Grid item xs={12} md={4}>
                <Controller
                  name="city"
                  control={control}
                  render={({ field }) => (
                    <TextField
                      select
                      fullWidth
                      size="small"
                      label="Tỉnh/thành phố (*)"
                      error={!!errors.city}
                      helperText={errors.city?.message}
                      {...field}
                    >
                      {cities.map((city) => (
                        <MenuItem key={city.id} value={city.id}>
                          {city.city_name}
                        </MenuItem>
                      ))}
                    </TextField>
                  )}
                />
              </Grid>

              <Grid item xs={12} md={8}>
                <TextField
                  label="Địa chỉ (*)"
                  fullWidth
                  size="small"
                  error={!!errors.address}
                  helperText={errors.address?.message}
                  {...register("address")}
                />
              </Grid>

              <Grid item xs={12}>
                <TextField
                  label="Website công ty"
                  fullWidth
                  size="small"
                  error={!!errors.companyWebsiteUrl}
                  helperText={errors.companyWebsiteUrl?.message}
                  {...register("companyWebsiteUrl")}
                />
              </Grid>

              <Grid item xs={12}>
                <Typography gutterBottom color="text.secondary">
                  Thông tin giới thiệu công ty
                </Typography>
                <CardCustomeEditor
                  value={companyDescription}
                  handleChange={setCompanyDescription}
                />
              </Grid>

              <Grid item xs={12} textAlign="center" mt={2}>
                <Button
                  type="submit"
                  variant="contained"
                  startIcon={<SaveIcon />}
                  sx={{ mr: 2 }}
                >
                  Lưu
                </Button>
                <Button variant="outlined" onClick={onReset}>
                  Không lưu
                </Button>
              </Grid>
            </Grid>
          </form>
        </Paper>
      </Container>

      {isLoading && <BackdropLoading />}
    </Box>
  );
};

export default memo(RequestRecruiterCompany);
