# Log Controller

## What is Log Controller?

Log Controller is a system, created using JAVA programming language. Basically it will store **.log** file logs to your database on change.

## Working structure

Created _program_ working structure is simple. It uses **.log** files. *Client* side will be waiting for **.log** file change (_**new log line to write in file**_), it will **catch the _change_**, takes **last inserted log**, sends it to **Server**. **Server** side gets the line, separates it by variables by your desired **_divider_** (_you can insert your divider in database_) and sends those variables into the *database*. 

## How to run it?

Lately, it will be added both Server.jar and Client.jar files to repository, when the project will be stated as a release (now it's in Beta state).

To run it now, it needs compiling main **two** classes :

**Server.java** in `Server/src/server/` folder.

**Client.java** in `Client/src/client/` folder.

Both of these **.jar** files needs _configuration_ file to run it. Here in this repository i've included both **example_client_config.json** and **example_server_config.json** _configuration_ files. In both of these _configuration_ files type the required information, **localhost ip address, port, database username, password, link the files in client_config.json** and it's done. **client_config.json** has to be in the same folder as **Client.jar** file, as well as **server_config.json** file has to be in the same folder as **Server.jar** file.

## Running through command line.

It's only available running through command line (console). 

If you're already know how to, that's great, if not here is the execution line:

`java -jar Server.jar` <- **Server.jar** has to be the **FIRST** you run.

If you're trying to run compiled code not from classpath where you compiled it, use :

`java -jar path/to/Server.jar`

Executing **Server.jar** , then you are able to run **Client.jar** as so:

`java -jar path/to/Client.jar [configuration-file]` <- **Client.jar** has to be the **FIRST** you run. Moreover it uses **argument** [file] after **Client.jar** , because it's how the **configuration file** is being loaded into the system.

If you are in the classpath where you compiled project, you can simply run it like so :

`java -jar Client.jar client_config.json`

**NOTE** that **Client** and **Server** configuration files **HAVE** to be in the **SAME** folder **WHERE** compiled project **.jar** files are.
