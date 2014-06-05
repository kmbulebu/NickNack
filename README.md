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
When a desired Event is detected, it's time to perform an Action. Actions are the 'doers' of Nick Nack. For example, a "set switch position" Action will turn a power (light) switch on, or off.

####Action Parameters
Action Parameters are provided by users to tell Nick Nack the details of how to carry out an action. The "set switch position" action will have a parameter that tells Nick Nack, and ultimately the provider whether to set the switch to the "on" or "off" position. The "set switch position" action will also likely have a parameter to identify which switch to instruct to change position. Parameters may have static values, such as "On", or it may use dynamic values, such as Event Attributes from the Event that caused the Action to run. 

###Plans
Plans are how a user gives Nick Nack instructions. Simplifying slightly, each Plan may read, "When Event, perform Action". However, a single Plan can state that multiple Events may trigger one or more Actions. 

##What's In the Box?

### Nick Nack Core
Nick Nack Core provides the core framework of Nick Nack, without an user interface. It defines Actions, Events, Filters, and Plans and how each of those interact with eachother. Most importantly, it defines the Java API that providers should implement to offer new Events and Actions in Nick Nack. 

### Nick Nack Rest Server
The Rest Server extends the core framework to the web as RESTful web services. These services may be used to power an AJAX web app, mobile apps, or integrate with other systems. 

### Nick Nack Web App (Planned)
A Web 2.0 application for creating Plans, configuring providers, and viewing event and action history. 

