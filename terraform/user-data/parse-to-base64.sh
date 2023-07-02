#!/bin/bash

# Read user data from userdata.sh file
user_data=$(cat user_data.sh)

# Base64 encode the user data
encoded_user_data=$(echo -n "$user_data" | base64)

# Print the encoded user data
echo $encoded_user_data
