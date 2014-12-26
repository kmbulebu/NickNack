XBMC Provider

Actions:
*Show Notification

Events:
*Player Started
*Player Paused
*Player Stopped

Configuration:

host<n> (host0, host1, etc).
Specifies the XBMC hosts available for actions and monitored for events. 

Sample:
```
<uuid12c97e3a707d472bad843f95897a9787>
  <name>XBMC</name>
  <host0>192.168.1.2:9090</host0>
  <host1>openelec_bedroom:9090</host1>
  <host2>192.168.1.3:9090</host2>
</uuid12c97e3a707d472bad843f95897a9787>
```
