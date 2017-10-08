# Deploy a web application to Heroku that uses Hibernate and Spring MVC

This repository serves as a good starting point for creating a Java webapp running on Heroku.
Its primary use is as an example app for a Heroku 101 course.

Follow instructions below to clone this repository and get your app running on Heroku!
There is also a section describing how you can use Heroku addons such as Blitz for load testing.

## Prerequisites

It is assumed that you have installed the Heroku Toolbelt: https://toolbelt.heroku.com/

## Clone this repository

Use for example Git Bash and navigate to a directory where you keep your code.
For the remainder of this readme, I will assume that you navigate to /c/git (C:/git in Windows).

    :::term
    $ git clone https://github.com/haugene/heroku-hibernate-springmvc-webapp.git
	
This will create the folder heroku-hibernate-springmvc-webapp.
Navigate into your new folder.

	:::term
    $ cd heroku-hibernate-springmvc-webapp

## Create a Heroku app where you can deploy your code

    :::term
    $ heroku apps:create
    Creating polar-savannah-7781... done, stack is cedar
    http://polar-savannah-7781.herokuapp.com/ | git@heroku.com:polar-savannah-7781.git
    Git remote heroku added

If you call heroku apps:create <application-name>, heroku will use that name instead of generating a new one.

As you can see, the command will also add a git remote called "heroku" to your git repository.

## Deploy to Heroku

To deploy your app to heroku, simply write:

    :::term
    git push heroku master

Congratulations! Your web app should now be up and running on Heroku. Open it in your browser with:

    :::term  
    $ heroku open
	
## Do some load testing on your app

### Provision the Blitz addon
	
	:::term
    $ heroku addons:add blitz:250
	
### Open Blitz UI
	
	:::term
    $ heroku addons:open blitz
	
### Run your load test
	
Your application has a mapping that accepts a "name" parameter on the overview page.
The given name will be added to the database.

This allows you to do a simple load test with the following url:
http://<your-app-name>.herokuapp.com/web/overview?name=TestName

After running your load test you should have somewhere around 6-7k entries in your Name table.
Before running a new test, you should delete all entries from the overview page. This is due to postgres dev database 10k row limit.
