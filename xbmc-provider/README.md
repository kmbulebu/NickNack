#Kodi (XBMC) Provider
http://kodi.tv/

## Supported Kodi (XBMC) versions
* Kodi 14.0 Helix
* XBMC 13.* Gotham

## Development Status
Active. Basic capabilities have been implemented to provide the commonly needed actions and events. Additional actions and events are planned.

## Actions:
* **Show Notification**

## Events:
* **Player Started**
* **Player Paused**
* **Player Stopped**

## Configuration:
* **hostN**: Specifies one or more XBMC hosts available for actions and monitored for events. N starts with zero (host0, host1, etc). 

### Sample nicknack_config.xml:
```
<uuid12c97e3a707d472bad843f95897a9787>
  <name>XBMC</name>
  <host0>192.168.1.2:9090</host0>
  <host1>openelec_bedroom:9090</host1>
  <host2>192.168.1.3:9090</host2>
</uuid12c97e3a707d472bad843f95897a9787>
```

## Known Issues
* None
