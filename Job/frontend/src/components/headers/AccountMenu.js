import React, { useState } from "react";
import Box from "@mui/material/Box";
import Avatar from "@mui/material/Avatar";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import ListItemIcon from "@mui/material/ListItemIcon";
import Divider from "@mui/material/Divider";
import IconButton from "@mui/material/IconButton";
import Tooltip from "@mui/material/Tooltip";
import HistoryIcon from "@mui/icons-material/History";
import AppsIcon from "@mui/icons-material/Apps";
import NewspaperIcon from "@mui/icons-material/Newspaper";
import WorkHistoryIcon from "@mui/icons-material/WorkHistory";
import PeopleAltIcon from "@mui/icons-material/PeopleAlt";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import FactCheckIcon from "@mui/icons-material/FactCheck";
import KeyIcon from "@mui/icons-material/Key";
import NotificationsIcon from "@mui/icons-material/Notifications";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { Badge } from "@mui/material";
import checkPermission from "../../permissions/CheckPermission";
import UserRole from "../../permissions/UserRole";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowRightFromBracket } from "@fortawesome/free-solid-svg-icons";
import cookie from "react-cookies";
import { logoutUser } from "../../store/actions/UserCreator";

export default function AccountMenu() {
  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);
  const user = useSelector((state) => state.user);
  const nav = useNavigate();
  const dispatch = useDispatch();

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    if (cookie.load("access_token")) {
      cookie.remove("access_token");
      cookie.remove("current_user");
      dispatch(logoutUser());
      nav("/login/");
    }
  };

  // Danh sách menu cho seeker
  const menuItemSeekers = [
    {
      key: "account",
      label: (
        <>
          <Avatar src={user.avatar} /> {user.username}
        </>
      ),
      onClick: () => nav("/seeker/account/"),
    },
    { key: "divider1", divider: true },
    {
      key: "general-management",
      label: "Quản lý chung",
      icon: <AppsIcon fontSize="small" />,
      onClick: () => nav("/seeker/general-management/"),
    },
    {
      key: "complete-profile",
      label: "Hoàn thiện hồ sơ",
      icon: <AccountCircleIcon fontSize="small" />,
      onClick: () => nav("/seeker/seeker-profile/"),
    },
    {
      key: "applied-jobs",
      label: "Việc làm đã ứng tuyển",
      icon: <FactCheckIcon fontSize="small" />,
      onClick: () => nav("/seeker/applied-jobs/"),
    },
    {
      key: "saved-jobs",
      label: "Việc làm đã lưu",
      icon: <WorkHistoryIcon fontSize="small" />,
      onClick: () => nav("/seeker/save-jobs/"),
    },
  ];

  // Danh sách menu cho recruiter
  const menuItemRecruiters = [
    {
      key: "account",
      label: (
        <>
          <Avatar src={user.avatar} /> {user.username}
        </>
      ),
      onClick: () => nav("/recruiter/account/"),
    },
    { key: "divider1", divider: true },
    {
      key: "general-management",
      label: "Quản lý chung",
      icon: <AppsIcon fontSize="small" />,
      onClick: () => nav("/recruiter/general-management/"),
    },
    {
      key: "post-new-job",
      label: "Đăng tin tuyển dụng",
      icon: <NewspaperIcon fontSize="small" />,
      onClick: () => nav("/recruiter/new-post/"),
    },
    {
      key: "posted-jobs",
      label: "Tin tuyển dụng đã đăng",
      icon: <HistoryIcon fontSize="small" />,
      onClick: () => nav("/recruiter/posted/"),
    },
    {
      key: "job-applicants",
      label: "Ứng viên ứng tuyển",
      icon: <PeopleAltIcon fontSize="small" />,
      onClick: () => nav("/recruiter/job-post-activity/"),
    },
  ];

  const renderMenuItems = (menuItems) =>
    menuItems.map((item) =>
      item.divider ? (
        <Divider key={item.key} />
      ) : (
        <MenuItem key={item.key} onClick={item.onClick}>
          {item.icon && <ListItemIcon>{item.icon}</ListItemIcon>}
          {item.label}
        </MenuItem>
      )
    );

  return (
    <>
      <Box sx={{ display: { sm: "flex", md: "flex" }, marginLeft: "auto" }}>
        <IconButton
          size="large"
          aria-label="show 17 new notifications"
          color="inherit"
          sx={{ display: { xs: "none", sm: "flex", md: "flex" } }}
        >
          <Badge badgeContent={1} color="error">
            <NotificationsIcon />
          </Badge>
        </IconButton>
      </Box>
      <Box sx={{ display: "flex", alignItems: "center", textAlign: "center" }}>
        <Tooltip title="Thiết lập tài khoản">
          <IconButton
            onClick={handleClick}
            size="small"
            sx={{ ml: 2 }}
            aria-controls={open ? "account-menu" : undefined}
            aria-haspopup="true"
            aria-expanded={open ? "true" : undefined}
          >
            <Avatar sx={{ width: 32, height: 32 }} src={user.avatar} />
          </IconButton>
        </Tooltip>
      </Box>

      <Menu
        anchorEl={anchorEl}
        id="account-menu"
        open={open}
        onClose={handleClose}
        onClick={handleClose}
        PaperProps={{
          elevation: 0,
          sx: {
            overflow: "visible",
            filter: "drop-shadow(0px 2px 8px rgba(0,0,0,0.32))",
            mt: 1.5,
            "& .MuiAvatar-root": {
              width: 32,
              height: 32,
              ml: -0.5,
              mr: 1,
            },
            "&:before": {
              content: '""',
              display: "block",
              position: "absolute",
              top: 0,
              right: 14,
              width: 10,
              height: 10,
              bgcolor: "background.paper",
              transform: "translateY(-50%) rotate(45deg)",
              zIndex: 0,
            },
          },
        }}
        transformOrigin={{ horizontal: "right", vertical: "top" }}
        anchorOrigin={{ horizontal: "right", vertical: "bottom" }}
      >
        {checkPermission(user, UserRole.SEEKER)
          ? renderMenuItems(menuItemSeekers)
          : checkPermission(user, UserRole.RECRUITER)
          ? renderMenuItems(menuItemRecruiters)
          : null}

        <MenuItem
          onClick={() =>
            checkPermission(user, UserRole.SEEKER)
              ? nav("/seeker/change-password/")
              : checkPermission(user, UserRole.RECRUITER)
              ? nav("/recruiter/change-password/")
              : null
          }
        >
          <ListItemIcon>
            <KeyIcon fontSize="small" />
          </ListItemIcon>
          Đổi mật khẩu
        </MenuItem>
        <Divider />
        <MenuItem style={{ color: "red" }} onClick={handleLogout}>
          <ListItemIcon>
            <FontAwesomeIcon icon={faArrowRightFromBracket} color="red" />
          </ListItemIcon>
          Đăng xuất
        </MenuItem>
      </Menu>
    </>
  );
}
