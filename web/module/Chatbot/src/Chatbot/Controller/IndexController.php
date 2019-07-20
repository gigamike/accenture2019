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

    $botman->hears('add Tide liquid detergent to my cart', function (BotMan $bot) {
      $authService = $this->serviceLocator->get('auth_service');
      if (!$authService->getIdentity()) {
        $bot->reply('Please signup or login to your account.');
      }else{
        $productId = 26;
        $quantity = 1;
        $userId = $this->identity()->id;

        $filter = array(
  				'product_id' => $productId,
  				'created_user_id' => $userId,
  			);
  			$order = array();
  			$carts = $this->getCartMapper()->getCarts(false, $filter, $order);
  			if(count($carts) > 0){
  				foreach ($carts as $cart) {
  					$quantity = $cart['quantity'] + $quantity;

  					$currentCart = $this->getCartMapper()->getCart($cart['id']);
  					if($currentCart){
  						$currentCart->setQuantity($quantity);
  						$this->getCartMapper()->save($currentCart);
  					}
  				}
  			}else{
  				$cart = new CartEntity;
  				$cart->setProductId($productId);
  				$cart->setQuantity($quantity);
  				$cart->setCreatedUserId($userId);
  				$this->getCartMapper()->save($cart);
  			}
        $bot->reply('Tide liquid detergent added to cart.');
      }
    });

    $botman->hears('add Ariel liquid detergent to my cart', function (BotMan $bot) {
      $authService = $this->serviceLocator->get('auth_service');
      if (!$authService->getIdentity()) {
        $bot->reply('Please signup or login to your account.');
      }else{
        $productId = 27;
        $quantity = 1;
        $userId = $this->identity()->id;

        $filter = array(
  				'product_id' => $productId,
  				'created_user_id' => $userId,
  			);
  			$order = array();
  			$carts = $this->getCartMapper()->getCarts(false, $filter, $order);
  			if(count($carts) > 0){
  				foreach ($carts as $cart) {
  					$quantity = $cart['quantity'] + $quantity;

  					$currentCart = $this->getCartMapper()->getCart($cart['id']);
  					if($currentCart){
  						$currentCart->setQuantity($quantity);
  						$this->getCartMapper()->save($currentCart);
  					}
  				}
  			}else{
  				$cart = new CartEntity;
  				$cart->setProductId($productId);
  				$cart->setQuantity($quantity);
  				$cart->setCreatedUserId($userId);
  				$this->getCartMapper()->save($cart);
  			}
        $bot->reply('Ariel liquid detergent added to cart.');
      }
    });

    $botman->hears('add Safeguard Soap to my cart', function (BotMan $bot) {
      $authService = $this->serviceLocator->get('auth_service');
      if (!$authService->getIdentity()) {
        $bot->reply('Please signup or login to your account.');
      }else{
        $productId = 28;
        $quantity = 1;
        $userId = $this->identity()->id;

        $filter = array(
  				'product_id' => $productId,
  				'created_user_id' => $userId,
  			);
  			$order = array();
  			$carts = $this->getCartMapper()->getCarts(false, $filter, $order);
  			if(count($carts) > 0){
  				foreach ($carts as $cart) {
  					$quantity = $cart['quantity'] + $quantity;

  					$currentCart = $this->getCartMapper()->getCart($cart['id']);
  					if($currentCart){
  						$currentCart->setQuantity($quantity);
  						$this->getCartMapper()->save($currentCart);
  					}
  				}
  			}else{
  				$cart = new CartEntity;
  				$cart->setProductId($productId);
  				$cart->setQuantity($quantity);
  				$cart->setCreatedUserId($userId);
  				$this->getCartMapper()->save($cart);
  			}
        $bot->reply('Safeguard Soap added to cart.');
      }
    });

    // Start listening
    $botman->listen();

    return $this->getResponse();
  }
}
