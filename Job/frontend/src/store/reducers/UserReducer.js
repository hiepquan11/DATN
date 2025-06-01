import * as Types from "../types";
import cookie from "react-cookies";

const initState = (() => {
  const userCookie = cookie.load("current_user");
  if (userCookie) {
    try {
      return typeof userCookie === "string" ? JSON.parse(userCookie) : userCookie;
    } catch {
      return null;
    }
  }
  return null;
})();

console.log("UserReducer initState", initState);

const UserReducer = (state = initState, action) => {
  switch (action.type) {
    case Types.USER_LOGIN:
      console.log("USER_LOGIN payload", action.payload);
      return action.payload;
    case Types.USER_LOGOUT:
      console.log("USER_LOGOUT");
      return null;
    case Types.USER_UPDATE:
       console.log("USER_UPDATE payload", action.payload);
      return action.payload;
    default:
      return state;
  }
};

export default UserReducer;
