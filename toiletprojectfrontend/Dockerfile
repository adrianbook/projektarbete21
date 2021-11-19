# pull official base image
FROM node:13.12.0-alpine

# set working directory
WORKDIR /

# add `/app/node_modules/.bin` to $PATH
ENV PATH /node_modules/.bin:$PATH

# install app dependencies
COPY react-form-handling/package.json ./
COPY react-form-handling/package-lock.json ./

# add app
COPY /react-form-handling ./
RUN npm install

# start app
CMD ["npm", "start"]