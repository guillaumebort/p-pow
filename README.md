# P-Pow

P-Pow (pronounce _/pi-pô/_ if you are french) makes [Pow](http://pow.cx) almost working with [Play framework](http://www.playframework.org) applications. It uses the Pow's **port proxying** feature to automatically register any Play application you run from the Play console, to its own **.dev** subdomain.

## First you need [Pow](http://pow.cx) on MacOS

It is ridiculously easy to install:

```bash
curl get.pow.cx | sh
```

## Then install P-Pow for your Play application

You need to add the **P-Pow sbt plugin** to your project build configuration. In `project/plugins.sbt` add the following lines to register the plugin:

```scala
resolvers += "Guillaume Bort" at "http://guillaume.bort.fr/repository"

addSbtPlugin("guillaume.bort" % "p-pow" % "0.1")
```

And then, in your `Build.scala` file, use the plugin for any Play project you want to expose via Pow:

```scala
val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
  playDefaultPort := PPow.EPHEMERAL,
  playOnStarted <+= name(PPow.register),
  playOnStopped <+= name(PPow.deregister)
)
```

## Now, see the magic

Once you have **P-Pow** enabled for your application, it will be automatically registered on the **.dev** domain name when you `run` it. 

For example:

```
       _            _ 
 _ __ | | __ _ _  _| |
| '_ \| |/ _' | || |_|
|  __/|_|\____|\__ (_)
|_|            |__/ 
             
play! 2.1-SNAPSHOT, http://www.playframework.org

> Type "help play" or "license" for more information.
> Type "exit" or use Ctrl+D to leave this console.

[helloworld] $ run
```

And then go to http://helloworld.dev/ to see your application running. 

## Why is it useful?

- Because you don't have to choose (and remember) unique port number anymore for each application. 
- Because it supports subdomains (such as http://customer1.helloworld.dev/).
- Because you skip all the cookie conflicts when running different applications on the same *localhost* domain.
- Because you can use it with http://xip.io/ and http://showoff.io/.

## Of course you can use different settings

Using the previous settings, the Play application will start on any free ephemeral port available, and use the application name as hostname.

Here is another possible configuration:

```scala
// Fixes the port
playDefaultPort := 1234, 

// And fixes the hostname to http://mycoolapp.dev/
playOnStarted <+= PPow.register("mycoolapp"), 
playOnStopped <+= PPow.deregister("mycoolapp")
```

Or this one could be useful if you have developers not using Pow:

```scala
playDefaultPort := { 
  if(file(System.getProperty("user.home") + "/.pow").exists) PPow.EPHEMERAL else 9000 
},
playOnStarted <+= name(PPow.register),
playOnStopped <+= name(PPow.deregister)
```
