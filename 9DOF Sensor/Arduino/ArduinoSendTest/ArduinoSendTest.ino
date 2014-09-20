
void setupNXTpin()
{

  for(int i=4; i<11; i++) {
    pinMode(i, OUTPUT); }
  pinMode(A0, OUTPUT);
  pinMode(A1, OUTPUT);

  //attachInterrupt(0, interruptFunction, RISING);  // setup pin 2 as an interrupt
}

void sendNXTdata(int numberToSend)
{
  int value = numberToSend&(1<<0);
  value = value >> 0;
  digitalWrite(A1, value);
  
  value = numberToSend&(1<<1);
  value = value >> 1;
  digitalWrite(A0, value);
  
  for (int dPin = 4; dPin < 11; dPin++)
  {
    int bitShift = dPin - 2;
    int value = numberToSend&(1<<bitShift);
    value = value >> bitShift;
    digitalWrite((14-dPin), value);
  }
}


void setup()
{
  setupNXTpin();
  Serial.begin(38400);
}

int counter = 0;

void loop()
{
  counter = counter + 1;
  counter = counter%512;
  sendNXTdata(counter);
  Serial.println(counter);
  delay(1000);
}

int mapNineBitToDegrees(int nineBitNum)
{
	float Degrees = 0;
	float NineBitNum = 0;
	float slope = 360.0/512.0;

	NineBitNum = (float)(nineBitNum);
	Degrees=slope*NineBitNum;
	Degrees=Degrees - 180.0;
	return (int)(Degrees);
}

