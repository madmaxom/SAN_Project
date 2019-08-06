// 
// 
// 

#include "AccelerometerDriver.h"


AccelerometerDriver::AccelerometerDriver()
{
	maxAcc = 1;
	pinX = 0;
	pinY = 1;
	pinZ = 2;
	Calibrate();
	ResetMaxValues();
}

AccelerometerDriver::~AccelerometerDriver()
= default;

AccelerometerDriver::AccelerometerDriver(const int x, const int y, const int z, const double max)
{
	maxAcc = max;
	pinX = x;
	pinY = y;
	pinZ = z;
	Calibrate();
	ResetMaxValues();
}

void AccelerometerDriver::GetRawData(double* values) const
{
	values[0] = GetRawX();
	values[1] = GetRawY();
	values[2] = GetRawZ();
}

double AccelerometerDriver::GetRawX() const
{
	return analogRead(pinX);
}

double AccelerometerDriver::GetRawY() const
{
	return analogRead(pinY);
}

double AccelerometerDriver::GetRawZ() const
{
	return analogRead(pinZ);
}

void AccelerometerDriver::GetVoltageData(double* values) const
{
	values[0] = GetVoltageX();
	values[1] = GetVoltageY();
	values[2] = GetVoltageZ();
}

double AccelerometerDriver::GetVoltageX() const
{
	const auto x = GetRawX();
	return x * 3.3 / 1024;
}

double AccelerometerDriver::GetVoltageY() const
{
	const auto y = GetRawY();
	return y * 3.3 / 1024;
}

double AccelerometerDriver::GetVoltageZ() const
{
	const auto z = GetRawZ();
	return z * 3.3 / 1024;
}

void AccelerometerDriver::GetGData(double* values)
{
	values[0] = GetGX();
	values[1] = GetGY();
	values[2] = GetGZ();
}

double AccelerometerDriver::GetGX()
{
	double valueX = 0;
	for (auto i = 0; i < 10; i++)
	{
		valueX += GetRawX();
	}
	valueX = valueX / 10;

	auto val = map(valueX, xMin, xMax, -1000, 1000) / 1000.0;
	if (abs(val) > absMaxX)
	{
		absMaxX = fabs(val);
	}
	return val;
}

double AccelerometerDriver::GetGY()
{
	double valueY = 0;
	for (auto i = 0; i < 10; i++)
	{
		valueY += GetRawY();
	}
	valueY = valueY / 10;

	auto val = map(valueY, yMin, yMax, -1000, 1000) / 1000.0;
	if (abs(val) > absMaxY)
	{
		absMaxY = fabs(val);
	}
	return val;
}

double AccelerometerDriver::GetGZ()
{
	double valueZ = 0;
	for (auto i = 0; i < 10; i++)
	{
		valueZ += GetRawZ();
	}
	valueZ = valueZ / 10;
	auto val = map(valueZ, zMin, zMax, -1000, 1000) / 1000.0;
	if (abs(val) > absMaxZ)
	{
		absMaxZ = fabs(val);
	}
	return val;
}

void AccelerometerDriver::Calibrate()
{
	xMin = 265;
	xMax = 398;

	yMin = 259;
	yMax = 393;

	zMin = 281;
	zMax = 413;

//	xMin = (valueX * 3.3 / 1024 - 0.3) * 1024 / 3.3;
//	xMax = (valueX * 3.3 / 1024 + 0.3) * 1024 / 3.3;
//	yMin = (valueY * 3.3 / 1024 - 0.3) * 1024 / 3.3;
//	yMax = (valueY * 3.3 / 1024 + 0.3) * 1024 / 3.3;
//	zMin = (valueZ * 3.3 / 1024 - 0.3) * 1024 / 3.3;
//	zMax = (valueZ * 3.3 / 1024 + 0.3) * 1024 / 3.3;
}

void AccelerometerDriver::ResetMaxValues()
{
	absMaxX = 0;
	absMaxY = 0;
	absMaxZ = 0;
}

void AccelerometerDriver::GetMaxValues(double* values) const
{
	values[0] = GetMaxValueX();
	values[1] = GetMaxValueY();
	values[2] = GetMaxValueZ();
}

double AccelerometerDriver::GetMaxValueX() const
{
	return absMaxX;
}

double AccelerometerDriver::GetMaxValueY() const
{
	return absMaxY;
}

double AccelerometerDriver::GetMaxValueZ() const
{
	return absMaxZ;
}

void AccelerometerDriver::GetTilt(double* values)
{
	const float alpha = 0.5;

	auto Xg = GetGX();
	auto Yg = GetGY();
	auto Zg = GetGZ();

	fXg = Xg * alpha + (fXg * (1.0 - alpha));
	fYg = Yg * alpha + (fYg * (1.0 - alpha));
	fZg = Zg * alpha + (fZg * (1.0 - alpha));

	//Roll & Pitch Equations
	const auto roll = (atan2(-fYg, fZg) * 180.0) / M_PI;
	const auto pitch = (atan2(fXg, sqrt(fYg * fYg + fZg * fZg)) * 180.0) / M_PI;

	values[0] = roll;
	values[1] = pitch;
}

bool AccelerometerDriver::Alarm()
{
	auto max = sqrt(pow(GetGX(), 2) + pow(GetGY(), 2) + pow(GetGX(), 2));
//	Serial.print(max);
	return max > maxAcc;
}

void AccelerometerDriver::SetMax(long max)
{
	this->maxAcc = max; 
}
