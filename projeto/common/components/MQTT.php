<?php
    namespace common\components;
    use Bluerhinos\phpMQTT;

    class MQTT {
        private const host = '192.168.1.92';
        private const port = 1883;
        private const topic = 'Carrinho';

        public $mqtt;

        public function __construct() {
            $this -> mqtt = new phpMQTT(
                self::host,
                self::port,
                'cliente',
            );
        }

        public function publishMessage($message) {
            if($this -> mqtt -> connect()) {
                $this -> mqtt -> publish(
                    self::topic,
                    $message,
                    0
                );

                $this -> mqtt -> close();
            }
        }
    }
?>