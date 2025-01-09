This is a scuffed and unsafe version of a SSH Connector (a bit like Putty or Kitty).

HOW TO USE:
After starting this software, you can see the main window. On the top you can select a connection. A "Dummy Connection" should already be there (you can delete this connection).
You can eye-button to make the password visible or hide it again.
By clicking on "CONNECT" a command-prompt should open. You must only enter the password. If autoCopyToClipboard is activated, you just need to perform a right-click and hit enter.

ADDING AND DELETING CONNECTIONS:
On the left upper corner there is a settings menu. Here you can add or delete a connection.

Add connections:
You need to enter the following things:
- Name: You can paste whatever name you want there. Please use names only once!
- Command: Here you need to enter the full command for connection with all parameters, e.g. "ssh admin@192.168.0.1 -p 2222"
- Password: You can paste there the password of your connection. Be aware that the password will not be encrypted so the password is normally shown in the xml file!

Delete connections:
You just need to select the connection and hit "DELETE". You will be asked if you really want to delete the connection.


SETTINGS
In the directory "config" there is a file named settings.txt.
In the file you can change the design (commented in the file) and (if you want to) the function to copy the password into the clipboard (You can deactivate it by setting the value to false).

In the directory "config" there is a file named sshList.xml.
In this file all data is saved of your connections. You can change them, but be cautious!
