import React from "react";
import * as Yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
import {
  Avatar,
  Button,
  CssBaseline,
  TextField,
  Grid,
  Box,
  Typography,
  Container,
  Alert,
  IconButton,
  Collapse,
  Divider,
  Stack,
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowLeft } from "@fortawesome/free-solid-svg-icons";

import Api, { endpoints } from "../../configs/Api";
import cookie from "react-cookies";
import { useDispatch } from "react-redux";
import { setUser } from "../../store/actions/UserCreator";

const theme = createTheme();

const Login = () => {
  const dispatch = useDispatch();
  const nav = useNavigate();

  const [openError, setOpenError] = React.useState(false);
  const [errorMessage, setErrorMessage] = React.useState("");

  const validationSchema = Yup.object().shape({
    username: Yup.string()
      .required("Tên người dùng không được để trống")
      .max(150, "Tên người dùng vượt quá độ dài cho phép"),
    password: Yup.string()
      .required("Mật khẩu không được phép để trống")
      .max(128, "Mật khẩu vượt quá độ dài cho phép"),
  });

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(validationSchema),
    defaultValues: {
      username: "",
      password: "",
    },
  });

  const handleLoginUser = async (data) => {
    try {
      const res = await Api.post(endpoints["auth-info"], {
        username: data.username,
        password: data.password,
      });

      if (res.data.statusCode === 200) {
        const { accessToken, refreshToken, user } = res.data.data;

        // Lưu cookie
        cookie.save("access_token", accessToken, {
          path: "/",
          maxAge: 60 * 15,
          secure: true,
        });

        cookie.save("refresh_token", refreshToken, {
          path: "/",
          maxAge: 60 * 60 * 24 * 7,
          secure: true,
        });

        cookie.save("current_user", user, {
          path: "/",
          maxAge: 60 * 60 * 24 * 7,
          secure: true,
        });

        // Cập nhật user vào Redux store
        dispatch(setUser(user));

        // Điều hướng về trang chủ
        nav("/");
      }
    } catch (err) {
      setOpenError(true);
      setErrorMessage(err.response?.data?.message || "Thông tin đăng nhập không hợp lệ");
      reset({ password: "" });
    }
  };

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            marginBottom: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "primary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Đăng nhập
          </Typography>

          <form onSubmit={handleSubmit(handleLoginUser)} noValidate>
            <Box sx={{ mt: 3 }}>
              <Grid container spacing={2}>
                <Grid item xs={12}>
                  <Collapse in={openError}>
                    <Alert
                      severity="error"
                      action={
                        <IconButton
                          aria-label="close"
                          color="inherit"
                          size="small"
                          onClick={() => setOpenError(false)}
                        >
                          <CloseIcon fontSize="inherit" />
                        </IconButton>
                      }
                      sx={{ mb: 2 }}
                    >
                      {errorMessage}
                    </Alert>
                  </Collapse>
                </Grid>

                <Grid item xs={12}>
                  <TextField
                    fullWidth
                    id="username"
                    label="Tên người dùng"
                    autoComplete="username"
                    autoFocus
                    error={!!errors.username}
                    helperText={errors.username?.message}
                    {...register("username")}
                  />
                </Grid>

                <Grid item xs={12}>
                  <TextField
                    fullWidth
                    name="password"
                    label="Mật khẩu"
                    type="password"
                    autoComplete="current-password"
                    error={!!errors.password}
                    helperText={errors.password?.message}
                    {...register("password")}
                  />
                </Grid>
              </Grid>

              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
              >
                Đăng nhập
              </Button>

              <Divider>Hoặc</Divider>

              <Stack
                direction="column"
                alignItems="center"
                spacing={1}
                sx={{ my: 2 }}
              ></Stack>

              <Grid container>
                <Grid item sx={{ margin: "0 auto", mb: 2 }}>
                  <Typography
                    variant="subtitle1"
                    gutterBottom
                    component={Link}
                    to="/"
                    style={{ textDecoration: "inherit" }}
                    color="grey.700"
                  >
                    <FontAwesomeIcon icon={faArrowLeft} /> Quay về trang chủ
                  </Typography>
                </Grid>
              </Grid>

              <Grid container>
                <Grid item xs>
                  <Link
                    to="/"
                    style={{ textDecoration: "inherit", color: "#1976d2" }}
                  >
                    Quên mật khẩu?
                  </Link>
                </Grid>
                <Grid item>
                  <Link
                    to="/register/"
                    style={{ textDecoration: "inherit", color: "#1976d2" }}
                  >
                    Bạn chưa có tài khoản? Đăng ký
                  </Link>
                </Grid>
              </Grid>
            </Box>
          </form>
        </Box>
      </Container>
    </ThemeProvider>
  );
};

export default Login;
