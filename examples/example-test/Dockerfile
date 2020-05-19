#Dockerfile Template for the specific projects that uses TestMaker. You must change the sequent values:
# CHROME_VERSION: a version that supports the ChromeDriver specified in the project
# FIREFOX_VERSION: a version that supports the GeckoDriver specified in the project
# the "test80" project with the name of the specific name of the project that uses TestMaker

#When executing docker run for an image with Chrome or Firefox please either mount -v /dev/shm:/dev/shm or use the flag --shm-size=2g to use the host's shared memory.
#example create image: docker build -t jorge2m/example-testmaker:latest .
#example container run: docker run -d -p 80:80 -p 443:443 --shm-size=2g -v "%CD%/dockerresults:/output-library" jorge2m/example-testmaker:latest
FROM ubuntu:19.10

RUN apt-get update -y \
	&& apt-get install -y wget \
	&& apt-get install -y gnupg2 \
	&& apt-get -qqy dist-upgrade \
	&& apt-get -qqy install software-properties-common gettext-base unzip \
	&& rm -rf /var/lib/apt/lists/* /var/cache/apt/*
	
#ENTRYPOINT ["increase-shared-memory.sh"]
#RUN echo "none /dev/shm tmpfs defaults,size=500m 0 0" >> /etc/fstab \
#	&& mount -o remount /dev/shm

#=======
# Java 8
#=======
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
ARG CHROME_VERSION=81.0.4044.138-1
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
	&& echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
	&& apt-get update -qqy \
	&& apt-get -qqy install google-chrome-stable=$CHROME_VERSION \
	&& rm /etc/apt/sources.list.d/google-chrome.list \
	&& rm -rf /var/lib/apt/lists/* /var/cache/apt/* \
	&& sed -i 's/"$HERE\/chrome"/"$HERE\/chrome" --no-sandbox/g' /opt/google/chrome/google-chrome

#=========
# Firefox
#=========
#ARG FIREFOX_VERSION=latest
ARG FIREFOX_VERSION=76.0.1
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


# Xvfb
RUN apt-get update -qqy \
	&& apt-get -qqy install xvfb \
	&& rm -rf /var/lib/apt/lists/* /var/cache/apt/*

COPY target/example-test*jar-with-dependencies.jar example-test.jar
EXPOSE 80
CMD xvfb-run -s "-screen 0 1024x768x24" java -cp example-test.jar com.github.jorge2m.example_test.access.ServerRest -port 80 -secureport 443
