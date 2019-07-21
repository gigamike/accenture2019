/* eslint-disable  func-names */
/* eslint-disable  no-console */

const Alexa = require('ask-sdk-core');
const request = require('request');
const https = require('https');
const await = require('await');
const Promise = require('promise');

const LaunchRequestHandler = {
  canHandle(handlerInput) {
    return handlerInput.requestEnvelope.request.type === 'LaunchRequest';
  },
  handle(handlerInput) {
    const speechText = 'Welcome to the Hackathron. King of online store. Do you want to order online? by linking your account and saying, add item to my cart.';
    const repromptText = 'Do you want to order online? by linking your account and saying, add item to my cart.';

    return handlerInput.responseBuilder
      .speak(speechText)
      .reprompt(speechText)
      .withSimpleCard('Welcome to the Hackathron. King of online store.', speechText)
      .getResponse();
  },
};

const HelloWorldIntentHandler = {
  canHandle(handlerInput) {
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
      && handlerInput.requestEnvelope.request.intent.name === 'HelloWorldIntent';
  },
  handle(handlerInput) {
    var speechText = 'Hello World Hackathron';

    return handlerInput.responseBuilder
      .speak(speechText)
      .withSimpleCard('Hello World Hackathron', speechText)
      .getResponse();
  },
};

const AddProductCarTideIntentHandler = {
  canHandle(handlerInput) {
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
      && handlerInput.requestEnvelope.request.intent.name === 'AddProductCarTideIntent';
  },
  handle(handlerInput) {
    const speechText = 'Item found. Tide liquid detergent. How many you want to order?';
    const repromptText = 'Item found. Tide liquid detergent. How many you want to order?';

    return handlerInput.responseBuilder
      .speak(speechText)
      .reprompt(speechText)
      .withSimpleCard('Hackathron. King of online store. Ordering Item', speechText)
      .getResponse();
  },
};

const AddProductCartArielIntentHandler = {
  canHandle(handlerInput) {
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
      && handlerInput.requestEnvelope.request.intent.name === 'AddProductCartArielIntent';
  },
  handle(handlerInput) {
    var speechText = 'Ariel liquid detergent added to cart';

    var url = `https://accenture2019.gigamike.net/api/cart-add?user_id=10&product_id=27&quantity=1`;
    request.get(url, (error, response, body) => {

    });

    return handlerInput.responseBuilder
      .speak(speechText)
      .withSimpleCard('Added Ariel liquid detergent to cart', speechText)
      .getResponse();
  },
};

const AddProductCartSafeguardSoapIntentHandler = {
  canHandle(handlerInput) {
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
      && handlerInput.requestEnvelope.request.intent.name === 'AddProductCartSafeguardSoapIntent';
  },
  handle(handlerInput) {
    var speechText = 'Safeguard Soap added to cart';

    var url = `https://accenture2019.gigamike.net/api/cart-add?user_id=10&product_id=28&quantity=1`;
    request.get(url, (error, response, body) => {

    });

    return handlerInput.responseBuilder
      .speak(speechText)
      .withSimpleCard('Added Safeguard Soap detergent to cart', speechText)
      .getResponse();
  },
};

const ItemCountIntentHandler = {
  canHandle(handlerInput) {
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
      && handlerInput.requestEnvelope.request.intent.name === 'ItemCountIntent';
  },
  handle(handlerInput) {
    var speechText = 'Tide liquid detergent added to cart';

    var url = `https://accenture2019.gigamike.net/api/cart-add?user_id=10&product_id=26&quantity=1`;
    request.get(url, (error, response, body) => {

    });

    return handlerInput.responseBuilder
      .speak(speechText)
      .withSimpleCard('Add Tide liquid detergent to cart', speechText)
      .getResponse();
  },
};

const HelpIntentHandler = {
  canHandle(handlerInput) {
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
      && handlerInput.requestEnvelope.request.intent.name === 'AMAZON.HelpIntent';
  },
  handle(handlerInput) {
    const speechText = 'Welcome to the Hackathron. King of online store. Do you want to order online? by linking your account and saying, add item to my cart.';
    const repromptText = 'Welcome to the Hackathron. King of online store. Do you want to order online? by linking your account and saying, add item to my cart.';

    return handlerInput.responseBuilder
      .speak(speechText)
      .reprompt(speechText)
      .withSimpleCard('Welcome to the Hackathron. King of online store.', speechText)
      .getResponse();
  },
};

const CancelAndStopIntentHandler = {
  canHandle(handlerInput) {
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
      && (handlerInput.requestEnvelope.request.intent.name === 'AMAZON.CancelIntent'
        || handlerInput.requestEnvelope.request.intent.name === 'AMAZON.StopIntent');
  },
  handle(handlerInput) {
    const speechText = 'Goodbye!';

    return handlerInput.responseBuilder
      .speak(speechText)
      .withSimpleCard('Welcome to the Hackathron. King of online store.', speechText)
      .getResponse();
  },
};

const SessionEndedRequestHandler = {
  canHandle(handlerInput) {
    return handlerInput.requestEnvelope.request.type === 'SessionEndedRequest';
  },
  handle(handlerInput) {
    console.log(`Session ended with reason: ${handlerInput.requestEnvelope.request.reason}`);

    return handlerInput.responseBuilder.getResponse();
  },
};

const ErrorHandler = {
  canHandle() {
    return true;
  },
  handle(handlerInput, error) {
    console.log(`Error handled: ${error.message}`);

    return handlerInput.responseBuilder
      .speak('Sorry, I can\'t understand the command. Please say again.')
      .reprompt('Sorry, I can\'t understand the command. Please say again.')
      .getResponse();
  },
};

const skillBuilder = Alexa.SkillBuilders.custom();

exports.handler = skillBuilder
  .addRequestHandlers(
    LaunchRequestHandler,
    HelloWorldIntentHandler,
    AddProductCarTideIntentHandler,
    ItemCountIntentHandler,
    AddProductCartArielIntentHandler,
    AddProductCartSafeguardSoapIntentHandler,
    HelpIntentHandler,
    CancelAndStopIntentHandler,
    SessionEndedRequestHandler
  )
  .addErrorHandlers(ErrorHandler)
  .lambda();
