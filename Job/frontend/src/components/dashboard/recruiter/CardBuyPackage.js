import {
  Box,
  Button,
  Card,
  CardContent,
  Grid,
  MenuItem,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { authApi, endpoints } from "../../../configs/Api";
import { useConfirm } from "material-ui-confirm";
import AlertCreator from "../../../store/actions/AlertCreator";
import { useDispatch } from "react-redux";

const CardBuyPackage = () => {
  const confirm = useConfirm();
  const dispatch = useDispatch();
  const user = useSelector((state) => state.user);
  const [packages, setPackages] = useState([]);
  const [selectedPackageId, setSelectedPackageId] = useState(null);

  useEffect(() => {
    const fetchPackages = async () => {
      try {
        const res = await authApi().get(endpoints["job-post-packages"]);
        setPackages(res.data);
      } catch (err) {
        console.error("Lỗi tải danh sách gói:", err);
      }
    };
    fetchPackages();
  }, []);

  const selectedPkg = packages.find((p) => p.id === selectedPackageId);

  const handleBuyPackage = async () => {
    if (!selectedPkg) return;

    try {
      await confirm({
        title: "Xác nhận mua gói",
        description: `
          Bạn sắp mua gói: ${selectedPkg.packageName}\n
          Giá: ${selectedPkg.price.toLocaleString()} VND\n
          Thời hạn: ${selectedPkg.durationDays} ngày\n
          Số lượt đăng: ${selectedPkg.postQuota} bài\n
          Bạn có chắc chắn muốn thanh toán qua VNPay?
        `,
        confirmationText: "Mua ngay",
        cancellationText: "Hủy",
      });

      const res = await authApi().post(endpoints["purchase-package"], null, {
        params: {
          userId: user.id,
          packageId: selectedPkg.id,
        },
      });

      const paymentUrl = res.data.data?.paymentUrl;
      if (paymentUrl) {
        window.location.href = paymentUrl;
      } else {
        dispatch(AlertCreator("warning", "Không có link thanh toán từ hệ thống."));
      }
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <Card>
      <CardContent>
        <Typography variant="h6" sx={{ mb: 2, fontWeight: "bold", color: "#0000ee" }}>
          | Mua gói đăng tuyển
        </Typography>

        <Stack spacing={2}>
          <TextField
            select
            label="Chọn gói đăng bài"
            fullWidth
            size="small"
            value={selectedPackageId || ""}
            onChange={(e) => setSelectedPackageId(Number(e.target.value))}
          >
            {packages.map((pkg) => (
              <MenuItem key={pkg.id} value={pkg.id}>
                {pkg.packageName} - {pkg.postQuota} bài / {pkg.durationDays} ngày - {pkg.price.toLocaleString()} VND
              </MenuItem>
            ))}
          </TextField>

          {selectedPkg && (
            <Box sx={{ p: 2, backgroundColor: "#f7f7f7", borderRadius: 1 }}>
              <Typography><strong>Tên gói:</strong> {selectedPkg.packageName}</Typography>
              <Typography><strong>Số lượt đăng:</strong> {selectedPkg.postQuota} bài</Typography>
              <Typography><strong>Thời hạn:</strong> {selectedPkg.durationDays} ngày</Typography>
              <Typography><strong>Giá:</strong> {selectedPkg.price.toLocaleString()} VND</Typography>
            </Box>
          )}

          <Button
            variant="contained"
            color="primary"
            onClick={handleBuyPackage}
            disabled={!selectedPkg}
          >
            Mua gói
          </Button>
        </Stack>
      </CardContent>
    </Card>
  );
};

export default CardBuyPackage;
