const checkPermission = (user, userRole) => {
  if (user !== null && Array.isArray(user.roles)) {
    return user.roles.some(
      role => role.roleName.toLowerCase() === userRole.toLowerCase()
    );
  }
  return false;
};

export default checkPermission;
