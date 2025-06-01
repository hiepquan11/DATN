import { authApi, endpoints } from "../../configs/Api";
import * as Types from "../types";
import cookie from "react-cookies";

// Set user (dùng sau khi login)
const setUser = (user) => {
  cookie.save("current_user", user, { path: "/", maxAge: 60 * 60 * 24 * 7 });
  return { type: Types.USER_LOGIN, payload: user };
};

// Lấy user từ API nếu có token
const fetchUser = () => async (dispatch) => {
  try {
    const token = cookie.load("access_token");
    if (token) {
      const res = await authApi().get(endpoints["current-user"]);
      if (res.status === 200) {
        dispatch(setUser(res.data));
      } else {
        cookie.remove("access_token");
        cookie.remove("refresh_token");
        cookie.remove("current_user");
        dispatch({ type: Types.USER_LOGOUT });
      }
    }
  } catch (err) {
    console.error("Fetch user failed", err);
    dispatch({ type: Types.USER_LOGOUT });
  }
};

const updateUser = () => async (dispatch) => {
  try {
    const res = await authApi().get(endpoints["current-user"]);
    if (res.status === 200) {
      dispatch(setUser(res.data));
    }
  } catch (err) {
    console.error("Update user failed", err);
  }
};

// Logout
const logoutUser = () => {
  cookie.remove("access_token");
  cookie.remove("refresh_token");
  cookie.remove("current_user");
  return { type: Types.USER_LOGOUT };
};

export { setUser, fetchUser, logoutUser, updateUser };
