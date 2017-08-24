# Log Controller

## What is Log Controller?

Log Controller is a system, created using JAVA programming language. It basically it will store .log file logs to your database on change.

## Working structure

Created _program_ working structure is simple. It uses **.log** files. *Client* side will be waiting for **.log** file change (_new log line to write in file_), it will *catch the _change_*, takes *last inserted log*, sends it to *Server*. *Server* side gets the line, separates it by variables by your desired *_divider_* (_you can insert your divider in database_) and sends those variables into the *database*. 

## How to run it?


