<?php

namespace Chatbot\Controller;

use Zend\Db\Adapter\Adapter;
use Zend\Mvc\Controller\AbstractActionController;
use Zend\View\Model\ViewModel;
use Zend\Session\Container;

use BotMan\BotMan\BotMan;
use BotMan\BotMan\BotManFactory;
use BotMan\BotMan\Drivers\DriverManager;

use Cart\Model\CartEntity;

class IndexController extends AbstractActionController
{
  public function getCartMapper()
  {
    $sm = $this->getServiceLocator();
    return $sm->get('CartMapper');
  }

  public function getUserMapper()
	{
		$sm = $this->getServiceLocator();
		return $sm->get('UserMapper');
	}

  public function indexAction()
  {
    $viewModel = new ViewModel();
    $viewModel->setVariables(array('key' => 'value'))
              ->setTerminal(true);
    return $viewModel;
  }

  public function serverAction()
  {
    $config = [];

    // Load the driver(s) you want to use
    DriverManager::loadDriver(\BotMan\Drivers\Web\WebDriver::class);

    // Create an instance
    $botman = BotManFactory::create($config);

    // Give the bot something to listen for.
    $botman->hears('hello', function (BotMan $bot) {
      $bot->reply('Welcome to the Hackathron. King of online store. Do you want to order online? by linking your account and saying, add item to my cart.');
    });

    // Start listening
    $botman->listen();

    return $this->getResponse();
  }
}
