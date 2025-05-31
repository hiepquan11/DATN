import * as React from "react";
import * as Yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { Link, useNavigate } from "react-router-dom";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { Divider, Stack } from "@mui/material";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowLeft } from "@fortawesome/free-solid-svg-icons";
import Alert from "@mui/material/Alert";
import IconButton from "@mui/material/IconButton";
import Collapse from "@mui/material/Collapse";
import CloseIcon from "@mui/icons-material/Close";
import { useForm } from "react-hook-form";
import Api, { endpoints } from "../../configs/Api";
import cookie from "react-cookies";
import { useDispatch } from "react-redux";
import { updateUser } from "../../store/actions/UserCreator";

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
        cookie.save("accessToken", res.data.data.accessToken, {
          path: "/",
          maxAge: 60 * 15, 
          secure: true,
        });

        cookie.save("refreshToken", res.data.data.refreshToken, {
          path: "/",
          maxAge: 60 * 60 * 24 * 7,
          httpOnly: false,
          secure: true,
        });

        cookie.save("current_user", JSON.stringify(res.data.data.user), {
          path: "/",
          maxAge: 60 * 60 * 24 * 7,
          httpOnly: false,
          secure: true,
        });

        dispatch(updateUser(res.data.data.user)); 

        nav("/"); 
      }
    } catch (err) {

      setOpenError(true);

      if (err.response?.data?.message) {
        setErrorMessage(err.response.data.message);
      } else {
        setErrorMessage("Thông tin đăng nhập không hợp lệ");
      }

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
                <Grid item xs={12} sm={12}>
                  <Collapse in={openError}>
                    <Alert
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
                      severity="error"
                    >
                      {errorMessage}
                    </Alert>
                  </Collapse>
                </Grid>
                <Grid item xs={12} sm={12}>
                  <TextField
                    fullWidth
                    id="username"
                    label="Tên người dùng"
                    name="username"
                    autoComplete="username"
                    autoFocus
                    error={!!errors.username}
                    helperText={errors.username ? errors.username.message : ""}
                    {...register("username")}
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    fullWidth
                    name="password"
                    label="Mật khẩu"
                    type="password"
                    id="password"
                    autoComplete="current-password"
                    error={!!errors.password}
                    helperText={errors.password ? errors.password.message : ""}
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
                justifyContent="space-around"
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
