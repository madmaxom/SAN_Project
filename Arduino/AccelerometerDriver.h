// AccelerometerDriver.h

#ifndef _ACCELEROMETERDRIVER_h
#define _ACCELEROMETERDRIVER_h

#if defined(ARDUINO) && ARDUINO >= 100
#include "arduino.h"
#else
	#include "WProgram.h"
#endif

class AccelerometerDriver
{
public:
	AccelerometerDriver();
	~AccelerometerDriver(); 
	AccelerometerDriver(int x, int y, int z, double max);

	void GetRawData(double* values) const;
	void GetVoltageData(double* values) const;
	void GetGData(double* values);
	void GetMaxValues(double* values) const; 
	void ResetMaxValues();
	void GetTilt(double* values);
	bool Alarm();
	void SetMax(long max);

	void GetVelocity(double* values); 
private:
	void Calibrate();
	double GetMaxValueX() const;
	double GetMaxValueY() const;
	double GetMaxValueZ() const;

	double GetRawX() const;
	double GetRawY() const;
	double GetRawZ() const;

	double GetVoltageX() const;
	double GetVoltageY() const;
	double GetVoltageZ() const;

	double GetGX();
	double GetGY();
	double GetGZ();

	int pinX;
	int pinY;
	int pinZ;

	double maxAcc;
	double xMax;
	double xMin;
	double yMax;
	double yMin;
	double zMax;
	double zMin;

	double absMaxX;
	double absMaxY;
	double absMaxZ;

	double fXg = 0;
	double fYg = 0;
	double fZg = 0;
};

#endif
