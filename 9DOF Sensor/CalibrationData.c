// "accel x,y,z (min/max) = X_MIN/X_MAX  Y_MIN/Y_MAX  Z_MIN/Z_MAX"
#define ACCEL_X_MIN ((float) -275)
#define ACCEL_X_MAX ((float) 261)
#define ACCEL_Y_MIN ((float) -246)
#define ACCEL_Y_MAX ((float) 256)
#define ACCEL_Z_MIN ((float) -280)
#define ACCEL_Z_MAX ((float) 251)

// Magnetometer (standard calibration mode)
// "magn x,y,z (min/max) = X_MIN/X_MAX  Y_MIN/Y_MAX  Z_MIN/Z_MAX"
#define MAGN_X_MIN ((float) -600)
#define MAGN_X_MAX ((float) 600)
#define MAGN_Y_MIN ((float) -600)
#define MAGN_Y_MAX ((float) 600)
#define MAGN_Z_MIN ((float) -600)
#define MAGN_Z_MAX ((float) 600)

// Magnetometer (extended calibration mode)
// Uncommend to use extended magnetometer calibration (compensates hard & soft iron errors)
//#define CALIBRATION__MAGN_USE_EXTENDED true
//const float magn_ellipsoid_center[3] = {0, 0, 0};
//const float magn_ellipsoid_transform[3][3] = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
const float magn_ellipsoid_center[3] = {44.0805, 84.8388, 4.76567};
const float magn_ellipsoid_transform[3][3] = {{0.772262, -0.00760499, -0.0272766}, {-0.00760499, 0.926243, 0.0323994}, {-0.0272766, 0.0323994, 0.981637}};


// Gyroscope
// "gyro x,y,z (current/average) = .../OFFSET_X  .../OFFSET_Y  .../OFFSET_Z
#define GYRO_AVERAGE_OFFSET_X ((float) 2.42)
#define GYRO_AVERAGE_OFFSET_Y ((float) 52.42)
#define GYRO_AVERAGE_OFFSET_Z ((float) -17.76)