import React, { Fragment, useState } from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";

import {
  AppBar,
  Toolbar,
  IconButton,
  Typography,
  Drawer,
  Box,
  Tab,
  Tabs,
} from "@mui/material";

import CloseIcon from "@mui/icons-material/Close";
import ViewComfyIcon from "@mui/icons-material/ViewComfy";
import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles(() => ({
  customHoverFocus: {
    textDecoration: "inherit",
    color: "inherit",
    "&:hover, &.Mui-focusVisible": {
      color: "#1976d2",
      fontWeight: "bold",
    },
  },
}));

const CardCareer = () => {
  const classes = useStyles();
  const [open, setOpen] = useState(false);
  const careers = useSelector((state) => state.careers.careers);

  const toggleDrawer = (open) => (event) => {
    if (
      event &&
      event.type === "keydown" &&
      (event.key === "Tab" || event.key === "Shift")
    ) {
      return;
    }
    setOpen(open);
  };

  const renderDrawerContent = () => (
    <Box
      role="presentation"
      onClick={toggleDrawer(false)}
      onKeyDown={toggleDrawer(false)}
    >
      <AppBar position="sticky">
        <Toolbar>
          <IconButton
            edge="start"
            color="inherit"
            onClick={toggleDrawer(false)}
            aria-label="close"
          >
            <CloseIcon />
          </IconButton>
          <Typography sx={{ ml: 2, flex: 1 }} variant="h6" component="div">
            Danh sách tất cả ngành nghề
          </Typography>
        </Toolbar>
      </AppBar>
      <Box
        sx={{
          display: "flex",
          flexWrap: "wrap",
          alignContent: "flex-start",
          p: 2,
        }}
      >
        {careers?.length > 0 &&
          careers.map((career) => (
            <Box
              key={career.id}
              sx={{
                width: {
                  xs: "100%",
                  sm: "50%",
                  md: "25%",
                  lg: "25%",
                  xl: "20%",
                },
                p: 1,
              }}
            >
              <Typography
                variant="body1"
                gutterBottom
                className={classes.customHoverFocus}
                component={Link}
                to={`/?career_id=${career.id}`}
              >
                {career.career_name}
              </Typography>
            </Box>
          ))}
      </Box>
    </Box>
  );

  return (
    <Fragment>
      <AppBar
        position="static"
        color="inherit"
        sx={{
          boxShadow: 0,
          borderBottom: 1,
          borderColor: "grey.200",
          color: "primary.main",
        }}
      >
        <Toolbar variant="dense">
          <IconButton
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
            onClick={toggleDrawer(true)}
          >
            <ViewComfyIcon />
          </IconButton>
          <Tabs
            variant="scrollable"
            scrollButtons
            allowScrollButtonsMobile
            value={false} // Không dùng state chọn tab
          >
            {careers?.length > 0 &&
              careers.map((career) => (
                <Tab
                  key={career.id}
                  label={
                    <Typography
                      variant="subtitle2"
                      sx={{ mx: 2, textTransform: "capitalize" }}
                      component={Link}
                      to={`/?career_id=${career.id}`}
                      style={{
                        textDecoration: "inherit",
                        color: "#1976d2",
                      }}
                    >
                      {career.career_name}
                    </Typography>
                  }
                />
              ))}
          </Tabs>
        </Toolbar>
      </AppBar>

      <Drawer anchor="top" open={open} onClose={toggleDrawer(false)}>
        {renderDrawerContent()}
      </Drawer>
    </Fragment>
  );
};

export default CardCareer;
