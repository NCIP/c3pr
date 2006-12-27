Notes to create a new user in oracle (default name assumed is 'c3pr')
---------------------------------------------------------------------
Execute the following in SQL Plus

1) create user c3pr identified by c3pr
2) connect c3pr as sysdba
 <give your password once it prompts for it>
3) grant create session to c3pr
4) grant all privileges to c3pr

Note - it is not recommended to grant all privileges to the user. You can restrict
the privileges (for now its fine)