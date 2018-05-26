// TemperatureDriver.h

#ifndef _TEMPERATUREDRIVER_h
#define _TEMPERATUREDRIVER_h

#if defined(ARDUINO) && ARDUINO >= 100
	#include "arduino.h"
#else
	#include "WProgram.h"
#endif

class TemperatureDriver
{
public:
	TemperatureDriver(); 
	~TemperatureDriver(); 
	TemperatureDriver(int input);
	double GetTemperature() const; 
	double GetMinMaxTemp(double* values);
	bool Alarm() const;
	void ResetMaxValues();
	void SetMaxValue(long max);
private:
	int pin;
	mutable double max = -100;
	mutable double min = 100; 
	int upperLimit = 25; 
	int lowerLimit = 15; 
};

#endif

