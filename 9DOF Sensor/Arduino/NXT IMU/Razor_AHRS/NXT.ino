#define VERSION1

#ifdef VERSION1
void setupNXTpin()
{

  for (int dPin = 0; dPin < 10; dPin++)
  {
    if(dPin != 2) pinMode(dPin, OUTPUT);
    else pinMode(dPin, INPUT);
  } 
  attachInterrupt(0, interruptFunction, RISING);  // setup pin 2 as an interrupt
}

void sendNXTdata(int numberToSend)
{
  for (int dPin = 0; dPin < 9; dPin++)
  {
    int value = numberToSend&(1<<dPin);
    value = value >> dPin;
    if(dPin != 6)      
      digitalWrite((8-dPin), value);
    else 
      digitalWrite(9, value);
  }
}
#endif

#ifdef VERSION2
void setupNXTpin()
{

  for(int i=4; i<11; i++) {
    pinMode(i, OUTPUT); 
  }
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
#endif

void interruptFunction()
{
  //what should be done when the interrupt is triggered goes here
}

