#Dockerfile Template for the specific projects that uses TestMaker. You must change the sequent values:
# CHROME_VERSION: a version that supports the ChromeDriver specified in the project
# FIREFOX_VERSION: a version that supports the GeckoDriver specified in the project
# NOTE: You must establish the latest Chrome Version stable. This will change in the future

#When executing docker run for an image with Chrome or Firefox please either mount -v /dev/shm:/dev/shm or use the flag --shm-size=2g to use the host's shared memory.
#example create image: docker build -t jorge2m/example-testmaker:latest .
#example container run: docker run -d -p 80:80 -p 443:443 --privileged -v "%CD%/dockerresults:/output-library" jorge2m/example-testmaker:latest
FROM ubuntu:20.04

RUN apt-get update -y \
	&& apt -y install locales \
	&& apt-get install -y wget \
	&& apt-get install -y gnupg2 \
	&& apt-get -qqy dist-upgrade \
	&& apt-get -qqy install software-properties-common gettext-base unzip \
	&& rm -rf /var/lib/apt/lists/* /var/cache/apt/*

RUN locale-gen en_US.UTF-8
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

#=======
# Java 8
#=======
ENV JAVA_TOOL_OPTIONS -Dfile.encoding=UTF8
RUN add-apt-repository ppa:ts.sch.gr/ppa \
	&& apt-get update \
	&& echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections \
	&& dpkg --configure -a \
	&& apt-get install openjdk-8-jdk-headless -y --force-yes \
	#&& apt-get install openjdk-8-jdk-headless -y --allow \
	&& rm /etc/java-8-openjdk/accessibility.properties

####################################################################################################
# Adding Google Chrome and ChromeDriver like described in
# https://github.com/SeleniumHQ/docker-selenium/blob/master/NodeChrome/Dockerfile

#=======
# Chrome
#=======
#ARG CHROME_VERSION="google-chrome-stable"
#List of versions in https://www.ubuntuupdates.org/ppa/google_chrome
ARG CHROME_VERSION=88.0.4324.96-1
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
	&& echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
	&& apt-get update -qqy \
	&& apt-get -qqy install google-chrome-stable=$CHROME_VERSION \
	&& rm /etc/apt/sources.list.d/google-chrome.list \
	&& rm -rf /var/lib/apt/lists/* /var/cache/apt/* \
	&& sed -i 's/"$HERE\/chrome"/"$HERE\/chrome" --no-sandbox/g' /opt/google/chrome/google-chrome
	
#apt-get update
#apt-get install wget
#wget http://dl.google.com/linux/deb/pool/main/g/google-chrome-unstable/google-chrome-unstable_73.0.3679.0-1_amd64.deb
#apt-get install -f ./google-chrome-unstable_73.0.3679.0-1_amd64.deb

#=============
# ChromeDriver 
# Is not necessary, in execution time TestMaker gets automatically the indicated ChromeDriver version indicated in the code
# but you can download the same version used in the project is you want accelerate a few seconds the execution
#=============
#ARG CHROME_DRIVER_VERSION=78.0.3904.70
#RUN if [ -z "$CHROME_DRIVER_VERSION" ]; \
#  then CHROME_MAJOR_VERSION=$(google-chrome --version | sed -E "s/.* ([0-9]+)(\.[0-9]+){3}.*/\1/") \
#    && CHROME_DRIVER_VERSION=$(wget --no-verbose -O - "https://chromedriver.storage.googleapis.com/LATEST_RELEASE_${CHROME_MAJOR_VERSION}"); \
#  fi \
#  && echo "Using chromedriver version: "$CHROME_DRIVER_VERSION \
#  && wget --no-verbose -O /tmp/chromedriver_linux64.zip https://chromedriver.storage.googleapis.com/$CHROME_DRIVER_VERSION/chromedriver_linux64.zip \
#  && rm -rf /opt/selenium/chromedriver \
#  && unzip /tmp/chromedriver_linux64.zip -d /opt/selenium \
#  && rm /tmp/chromedriver_linux64.zip \
#  && mv /opt/selenium/chromedriver /opt/selenium/chromedriver-$CHROME_DRIVER_VERSION \
#  && chmod 755 /opt/selenium/chromedriver-$CHROME_DRIVER_VERSION \
#  && ln -fs /opt/selenium/chromedriver-$CHROME_DRIVER_VERSION /usr/bin/chromedriver

####################################################################################################
# Adding Google Firefox and GeckoDriver like described in
# https://github.com/SeleniumHQ/docker-selenium/blob/master/NodeFirefox/Dockerfile

#=========
# Firefox
#=========
#ARG FIREFOX_VERSION=latest
#List of versions in https://download-installer.cdn.mozilla.net/pub/firefox/releases/
ARG FIREFOX_VERSION=80.0
RUN FIREFOX_DOWNLOAD_URL=$(if [ $FIREFOX_VERSION = "latest" ] || [ $FIREFOX_VERSION = "nightly-latest" ] || [ $FIREFOX_VERSION = "devedition-latest" ]; then echo "https://download.mozilla.org/?product=firefox-$FIREFOX_VERSION-ssl&os=linux64&lang=en-US"; else echo "https://download-installer.cdn.mozilla.net/pub/firefox/releases/$FIREFOX_VERSION/linux-x86_64/en-US/firefox-$FIREFOX_VERSION.tar.bz2"; fi) \
  && apt-get update -qqy \
  && apt-get -qqy --no-install-recommends install firefox \
  && rm -rf /var/lib/apt/lists/* /var/cache/apt/* \
  && wget --no-verbose -O /tmp/firefox.tar.bz2 $FIREFOX_DOWNLOAD_URL \
  && apt-get -y purge firefox \
  && rm -rf /opt/firefox \
  && tar -C /opt -xjf /tmp/firefox.tar.bz2 \
  && rm /tmp/firefox.tar.bz2 \
  && mv /opt/firefox /opt/firefox-$FIREFOX_VERSION \
  && ln -fs /opt/firefox-$FIREFOX_VERSION/firefox /usr/bin/firefox

#============
# GeckoDriver
# Is not necessary, in execution time TestMaker gets automatically the indicated ChromeDriver version indicated in the code
# but you can download the same version used in the project is you want accelerate a few seconds the execution
#============
#ARG GECKODRIVER_VERSION=latest
#ARG GECKODRIVER_VERSION=0.26.0
#RUN GK_VERSION=$(if [ ${GECKODRIVER_VERSION:-latest} = "latest" ]; then echo "0.26.0"; else echo $GECKODRIVER_VERSION; fi) \
#  && echo "Using GeckoDriver version: "$GK_VERSION \
#  && wget --no-verbose -O /tmp/geckodriver.tar.gz https://github.com/mozilla/geckodriver/releases/download/v$GK_VERSION/geckodriver-v$GK_VERSION-linux64.tar.gz \
#  && rm -rf /opt/geckodriver \
#  && tar -C /opt -zxf /tmp/geckodriver.tar.gz \
#  && rm /tmp/geckodriver.tar.gz \
#  && mv /opt/geckodriver /opt/geckodriver-$GK_VERSION \
#  && chmod 755 /opt/geckodriver-$GK_VERSION \
#  && ln -fs /opt/geckodriver-$GK_VERSION /usr/bin/geckodriver


# Xvfb
RUN apt-get update -qqy \
	&& apt-get -qqy install xvfb \
	&& rm -rf /var/lib/apt/lists/* /var/cache/apt/*

COPY target/example-test*jar-with-dependencies.jar example-test.jar

#USER root
#COPY ["docker-entrypoint.sh", "/docker-entrypoint.sh"]
#RUN ["chmod", "+x", "/docker-entrypoint.sh"]
#COPY ["docker-entrypoint.sh", "/entrypoint2.sh"]

#This is needed for remove DOS-Return Line characters
#RUN dos2unix /entrypoint.sh \ 
#	&& apt-get --purge remove -y dos2unix \ 
#	&& rm -rf /var/lib/apt/lists/*

#RUN sed -i 's/\r$//' /entrypoint2.sh

EXPOSE 80
#ENTRYPOINT ["/docker-entrypoint.sh"]
#CMD /bin/bash -c "sh /entrypoint.sh"
#CMD ["sh", "-c", "xvfb-run -s "-screen 0 1024x768x24" java -cp example-test.jar com.github.jorge2m.example_test.access.ServerRest -port 80 -secureport 443"]
CMD echo "none /dev/shm tmpfs defaults,size=2g 0 0" >> /etc/fstab ; mount -o remount /dev/shm ; xvfb-run -s "-screen 0 1024x768x24" java -cp example-test.jar com.github.jorge2m.example_test.access.ServerRest -port 80 -secureport 443
