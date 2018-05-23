// 
// 
// 

#include "SpeedDriver.h"


void SpeedDriver::GetRawData(double* values) const
{
	values[0] = GetRawX();
	values[1] = GetRawY();
	values[2] = GetRawZ();
}

double SpeedDriver::GetRawX() const
{
	return analogRead(pinX);
}

double SpeedDriver::GetRawY() const
{
	return analogRead(pinY);
}

double SpeedDriver::GetRawZ() const
{
	return analogRead(pinZ);
}