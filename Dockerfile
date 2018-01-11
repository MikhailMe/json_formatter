FROM maven:3.3-jdk-8-onbuild
CMD ["java","-jar","target/formatter-0.1-jar-with-dependencies.jar"]
EXPOSE 80
#  sudo docker build -t formatter .
#  sudo docker run -it -p 8000:80 formatter


# for gradle
# ./gradlew docker && docker run -t —rm -p 80:80 formatter:0.1
# curl -s --data-binary @filename.json http://localhost
