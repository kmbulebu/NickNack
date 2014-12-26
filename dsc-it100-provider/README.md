# DSC Security System Provider
http://www.dsc.com/
https://github.com/kmbulebu/dsc-it100-java

## Supported Hardware
* DSC IT-100 Serial Integration Module

## Development Status
Active. Basic capabilities have been implemented to provide the commonly needed actions and events. Additional actions and events are planned.

## Actions:
* **Arm Partition in Away**
* **Arm Partition in Away without Entry Delay**
* **Arm Partition in Away with user code**
* **Arm Partition in Stay**
* **Disarm Partition**
* **Trigger Command Output**

## Events:
* **Zone Opened or Restored**
* **Partition in Alarm**
* **Partition Armed**
* **Partition Disarmed**
* **Entry Delay in Progress**
* **Exit Delay in Progress**

## Configuration:
There are two method for connecting to an IT-100. The first is via a serial port of the system running NickNack. 
The second is via TCP to a remote system with the IT-100. Ser2net was used to remote host an IT-100 during the development of this provider.

* **serialPort**: Serial port connection only. Specifies the COM port (Windows) or device (*nix) of the serial port with the IT-100.
* **host**: TCP connection only. Specifies the IP or hostname of the remote host.
* **port**: TCP connection only. Specifies the listening TCP port of the remote host.

### Sample Serial Port nicknack_config.xml:
Not Yet Implemented.

### Sample TCP nicknack_config.xml:
```
<uuid873b5fe3b69d4c80a10e9c04c0673776>
  <name>DSC Security System</name>
  <host>192.168.1.2</host>
  <port>2000</port>
</uuid873b5fe3b69d4c80a10e9c04c0673776>
```

## Known Issues
* Serial port configuration is not yet implemented.
* Envisalink has nearly identical commands but has not yet been tested.
