[![Build Status](https://drone.io/github.com/kmbulebu/NickNack/status.png)](https://drone.io/github.com/kmbulebu/NickNack/latest)
#NickNack 
##Overview
Nick Nack is an event driven automation engine for the "Internet of Things." Most homes are now equipped with at least one internet connected TV, appliance, security system, stereo receiver, or other device. Additionally, we find Home Theater PC software, Internet services, such as email and weather updates.  Nick Nack aims to offer an easy way to create meaningful connections between these providers.

##How it Works
At the heart of Nick Nack are Events and Actions. Each provider exposes Events and Actions to Nick Nack. For example, a TV may allow Nick Nack to control it with Actions such as "Volume Up", or "Switch to Channel N". It may generate events to notify Nick Nack of when important things happen, such as "TV is powered On." Perhaps we also have a light switch dimmer provider that offers an Action, "Dim Lights." Now, imagine we want to dim the lights whenever the TV is powered on. Nick Nack allows us say, "When the TV is powered on, dim the lights."

###Events
Providers create Events in Nick Nack to let us know when interesting things happen. A TV may notify us when it is powered on, or changes channels. A thermostat may notify us when the ambient temperature changes, or the furnace turns on. On a more basic level, Nick Nack's built in clock notifies us whenever the time changes.  Events are the starting point for all things in NickNack. No Action will ever be taken without an Event to trigger it.

####Event Attributes
Events alone have very little associated data. They include only a name and description, such as "TV Changed Channel". Event Attributes provide us with additional details about the event. At a minimum, the Event Attributes will identify what generated the event, and additional details about the event, such as the new state. A "TV Changed Channel" event should have attributes that tell us which TV changed channels (IP Address or some other identifier), and what channel number is now showing on the TV. An even richer TV provider may give additional details, such as the name of the program on the new channel, HD or SD, etc. It is important that providers create a rich set of Event Attributes, as these attributes may be used to filter Events, or may even be passed as parameters to Actions. 

###Actions

####Action Parameters


