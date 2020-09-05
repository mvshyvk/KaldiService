# Dockerfile для сборки образа проекта распознавания речи
FROM pykaldi/pykaldi

# Настройка окружения
ENV PATH /usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/kaldi/src/featbin:/kaldi/src/ivectorbin:/kaldi/src/online2bin:/kaldi/src/rnnlmbin:/kaldi/src/fstbin:$PATH
ENV LC_ALL C.UTF-8
ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN apt-get update
RUN apt-get install -y llvm-8 ffmpeg
RUN LLVM_CONFIG=/usr/bin/llvm-config-8 pip3 install enum34 llvmlite numba

# Deploying Maven & Tomcat
RUN apt-get install maven tomcat8 --yes

# Setting up environment variables for Maven & Tomcat
RUN echo $(/usr/bin/env java -XshowSettings:properties -version 2>&1 | grep "java.home" | sed -e 's/java.home\s*=//;s/ //g;') > /tmp/JAVA_HOME
ENV CATALINA_HOME /usr/share/tomcat8
ENV CATALINA_BASE /speech_recognition/service/tomcat

# Установка необходимых python-библиотек
RUN pip install --upgrade pip \
	tqdm \
	pandas \
	matplotlib \
	seaborn \
	librosa \
	sox \
	pysubs2 \
	flask \
	soundfile

# Working folder preparation
RUN mkdir speech_recognition	
WORKDIR speech_recognition
COPY . ./
RUN echo "cat motd" >> /root/.bashrc
RUN rm -r $CATALINA_BASE
RUN mkdir $CATALINA_BASE
RUN mkdir $CATALINA_BASE/temp
RUN mkdir $CATALINA_BASE/logs
RUN mkdir $CATALINA_BASE/conf
RUN mkdir $CATALINA_BASE/webapps

# Building KaldiService
WORKDIR  /speech_recognition/service/KaldiService
RUN export JAVA_HOME=$(cat /tmp/JAVA_HOME); mvn compile war:war

# Setting up Tomcat
RUN cp /etc/tomcat8/server.xml $CATALINA_BASE/conf
RUN cp /speech_recognition/service/KaldiService/target/KaldiService.war $CATALINA_BASE/webapps

# Defaults preparation
WORKDIR /speech_recognition
EXPOSE 8080/tcp

# Starting Tomact & KaldiService
CMD export JAVA_HOME=$(cat /tmp/JAVA_HOME); $CATALINA_HOME/bin/startup.sh; /bin/bash
