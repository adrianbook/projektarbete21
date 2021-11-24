# pull official base image
FROM node:14.16.0-alpine

# set working directory
WORKDIR /

# add `/app/node_modules/.bin` to $PATH
ENV PATH /node_modules/.bin:$PATH

# install app dependencies
COPY package.json ./
COPY package-lock.json ./

# add app
COPY src ./src
COPY public ./public
RUN npm install

# start app
CMD ["npm", "start"]