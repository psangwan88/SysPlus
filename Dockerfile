# Step 1: Use an official Ubuntu base image
FROM ubuntu:20.04

# Step 2: Install dependencies
RUN apt-get update && apt-get install -y \
    openjdk-8-jdk \
    curl \
    unzip \
    wget \
    git \
    ca-certificates \
    && apt-get clean

# Step 3: Set environment variables for Java
ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

# Step 4: Install Node.js (required for Playwright)
RUN curl -sL https://deb.nodesource.com/setup_16.x | bash - && \
    apt-get install -y nodejs

# Step 5: Install Playwright for Java (requires Node.js)
RUN npm install -g playwright

# Step 6: Install Maven
RUN apt-get install -y maven

# Step 7: Set working directory
WORKDIR /app

# Step 8: Copy your project files (pom.xml, source files, etc.)
COPY pom.xml .
COPY src ./src
COPY testNGFiles ./testNGFiles
COPY config ./config

# Step 9: Install Playwright browsers (optional but recommended)
RUN playwright install --with-deps
# Step 10: Run Maven tests
CMD ["mvn", "clean", "test", "-DsuiteXmlFile=testNGFiles/tests.xml"]
