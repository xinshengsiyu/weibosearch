Weibo search just for Sina
===========
##what can weibosearch do?
Like a spider,grab the messages from sina site by keywords, once no more than 20 messages will be returned. Stored in mongoDB.

##How to use?
* Java environment needed
* Clone the project and config the cpsmartbox.properties. By the way, in MySQL DataBase the cloumn name of keyword must be `name`, you can config the table name in the config file.
* About Cookie, first logined the Sina site and search a keyword, must be logined status, then use chrome get the cookie
* SinaAppKey: create a application on http://open.weibo.com/, then you can get the appKey, not online is Ok.
* Config MySQL and MongoDB, then use ant to build the project. cd to dist folder, use this command in cli.
```bash
 java -jar search.jar
```

##Issues
Any issues you can contact with me.

* Mail(893157632@qq.com)
* QQ: 893157632
* weibo: [@想死的骷髅](http://weibo.com/xinshengsiyu)
