// 
// 
// 

#include "BluetoothDriver.h"


BluetoothDriver::BluetoothDriver() : serial(12,13)
{
	inputString.reserve(200); 
}

BluetoothDriver::BluetoothDriver(int rx, int tx) :serial(rx, tx)
{
	inputString.reserve(200);
}

BluetoothDriver::~BluetoothDriver()
{
}

bool BluetoothDriver::IsConnected()
{
	return connected; 
}

void BluetoothDriver::Disconnect()
{
	serial.end(); 
}

void BluetoothDriver::Connect()
{
	serial.begin(9600);
	serial.setTimeout(25); 
	while (connected == false)
	{
		if (serial.available() > 0) {
			inputString = serial.readStringUntil('*');
			if (inputString.indexOf(START_COMMUNICATION) > 0)
			{
				serial.write(START_COMMUNICATION);
				connected = true; 
			}
		}
	}
}

void BluetoothDriver::Send(const String& msg)
{
	serial.write(msg.c_str());
	// while (true)
	// {
	// 	if (serial.available() > 0) {
	// 		inputString = serial.readString();
	// 		Serial.println(inputString);
	// 		if (inputString.indexOf(ACK) >= 0)
	// 		{
	// 			break;
	// 		}
	// 	}
	// }
}
