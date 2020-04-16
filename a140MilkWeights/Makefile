.PHONY = make jar runjar test clean

# replace with path to your javac,java,jar,javafx installations
JC = /Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home/bin/javac
JAR = /Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home/bin/jar
JAVA = /Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home/bin/java
MP = --module-path /Users/benjaminraffel/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml -Dfile.encoding=UTF-8
CP = -classpath /Users/benjaminraffel/Documents/MilkWeightsProject/a140MilkWeights:/Users/benjaminraffel/javafx-sdk-11.0.2/lib/javafx-swt.jar:/Users/benjaminraffel/javafx-sdk-11.0.2/lib/javafx.base.jar:/Users/benjaminraffel/javafx-sdk-11.0.2/lib/javafx.controls.jar:/Users/benjaminraffel/javafx-sdk-11.0.2/lib/javafx.fxml.jar:/Users/benjaminraffel/javafx-sdk-11.0.2/lib/javafx.graphics.jar:/Users/benjaminraffel/javafx-sdk-11.0.2/lib/javafx.media.jar:/Users/benjaminraffel/javafx-sdk-11.0.2/lib/javafx.swing.jar:/Users/benjaminraffel/javafx-sdk-11.0.2/lib/javafx.web.jar
APP = application.Main

#CLASSPATH = .:junit-platform-console-standalone-1.5.2.jar:json-simple-1.1.1.jar

make: 
	$(JC) $(MP) $(CP) -d . application/*.java

run:
	$(JAVA) $(MP) $(CP) application.Main

fx: 
	$(JC) $(MP) $(CP) -d . application/*.java

fxrun:
	$(JAVA) $(MP) $(CP) $(APP)

jar: 
	$(JAR) cvmf manifest.txt executable.jar .

runjar:
	java $(MP) -jar executable.jar

zip:
	zip team.zip application/* *

test: 
	javac $(MP) -cp $(CLASSPATH) *.java
	java -jar junit-platform-console-standalone-1.5.2.jar --class-path $(CLASSPATH) -p ""

clean:
	\rm application/*.class
	\rm executable.jar
