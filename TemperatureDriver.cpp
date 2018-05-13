#include "TemperatureDriver.h"

#define NUMSAMPLES 5
#define SERIESRESISTOR 20000
#define THERMISTORNOMINAL 10000
#define TEMPERATURENOMINAL 25
#define BCOEFFICIENT 3900

int samples[NUMSAMPLES];

TemperatureDriver::TemperatureDriver()
{
	pin = 7;
}

TemperatureDriver::~TemperatureDriver()
= default;

TemperatureDriver::TemperatureDriver(int pin)
{
	this->pin = pin;
}

double TemperatureDriver::GetTemperature() const
{
	int i;

	// take N samples in a row, with a slight delay
	for (i = 0; i < NUMSAMPLES; i++)
	{
		samples[i] = analogRead(pin);
		delay(10);
	}

	// average all the samples out
	float average = 0;
	for (i = 0; i < NUMSAMPLES; i++)
	{
		average += samples[i];
	}
	average /= NUMSAMPLES;

	// convert the value to resistance
	average = 1023 / average - 1;
	average = SERIESRESISTOR / average;

	auto temperature = average / THERMISTORNOMINAL; // (R/Ro)
	temperature = log(temperature); // ln(R/Ro)
	temperature /= BCOEFFICIENT; // 1/B * ln(R/Ro)
	temperature += 1.0 / (TEMPERATURENOMINAL + 273.15); // + (1/To)
	temperature = 1.0 / temperature; // Invert
	temperature -= 273.15;
	if (temperature < min)
	{
		min = temperature;
	}
	if (temperature > max)
	{
		max = temperature;
	}
	return temperature;
}

double TemperatureDriver::GetMinMaxTemp(double* values)
{
	values[0] = min;
	values[1] = max;
}

bool TemperatureDriver::Alarm() const
{
	return max > upperLimit || min < lowerLimit;
}

void TemperatureDriver::ResetMaxValues()
{
	upperLimit = 25;
	lowerLimit = -25;
}

void TemperatureDriver::SetMaxValue(long max)
{
	this->upperLimit = max; 
}
