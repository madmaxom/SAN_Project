#include "DisplayDriver.h"

DisplayDriver::DisplayDriver(): serial(0, 13,false)
{
	serial.begin(9600);
}

DisplayDriver::~DisplayDriver()
= default;

void DisplayDriver::ClearDisplay()
{
	ResetCursor();
	serial.write("                ");
	serial.write("                ");
}

void DisplayDriver::ResetCursor()
{
	serial.write(254);
	serial.write(128);
}

void DisplayDriver::WriteFirstLine(const String& msg)
{
	ClearFistLine(); 
	serial.write(254);
	serial.write(128);
	serial.write(msg.c_str());
}

void DisplayDriver::WriteSecondLine(const String& msg)
{
	ClearSecondLine(); 
	serial.write(254);
	serial.write(192); 
	serial.write(msg.c_str());
}

void DisplayDriver::WritePaging(char page)
{
	serial.write(254);
	serial.write(143);
	serial.write(page); 
}

void DisplayDriver::ClearFistLine()
{
	serial.write(254);
	serial.write(128);
	serial.write("                ");
}

void DisplayDriver::ClearSecondLine()
{
	serial.write(254);
	serial.write(192);
	serial.write("                ");
}
