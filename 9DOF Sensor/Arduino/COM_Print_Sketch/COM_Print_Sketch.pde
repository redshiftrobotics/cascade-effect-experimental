  import processing.serial.*;
  
  final static int SERIAL_PORT_NUM = 1;
  Serial serial;
  final static int SERIAL_PORT_BAUD_RATE = 38400;
  
  void setup()
  {
    // Setup serial port I/O
    println("AVAILABLE SERIAL PORTS:");
    println(Serial.list());
    String portName = Serial.list()[SERIAL_PORT_NUM];
    println();
    println("HAVE A LOOK AT THE LIST ABOVE AND SET THE RIGHT SERIAL PORT NUMBER IN THE CODE!");
    println("  -> Using port " + SERIAL_PORT_NUM + ": " + portName);
    serial = new Serial(this, portName, SERIAL_PORT_BAUD_RATE);
  }
