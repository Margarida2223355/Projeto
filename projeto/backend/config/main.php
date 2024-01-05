<?php
$params = array_merge(
    require __DIR__ . '/../../common/config/params.php',
    require __DIR__ . '/../../common/config/params-local.php',
    require __DIR__ . '/params.php',
    require __DIR__ . '/params-local.php'
);

return [
    'id' => 'app-backend',
    'basePath' => dirname(__DIR__),
    'controllerNamespace' => 'backend\controllers',
    'bootstrap' => ['log'],
    'modules' => [
        'api' => [
            'class' => 'app\modules\api\ModuleAPI',
        ],
        'debug' => [
            'class' => 'yii\debug\Module',
        ],
    ],
    'components' => [
        'request' => [
            'csrfParam' => '_csrf-backend',
            'parsers' => [
                'application/json' => 'yii\web\JsonParser',
            ],
        ],
        'user' => [
            'identityClass' => 'common\models\User',
            'enableAutoLogin' => true,
            'identityCookie' => ['name' => '_identity-backend', 'httpOnly' => true],
        ],
        'session' => [
            // this is the name of the session cookie used for login on the backend
            'name' => 'advanced-backend',
        ],
        'log' => [
            'traceLevel' => YII_DEBUG ? 3 : 0,
            'targets' => [
                [
                    'class' => \yii\log\FileTarget::class,
                    'levels' => ['error', 'warning'],
                ],
            ],
        ],
        'errorHandler' => [
            'errorAction' => 'site/error',
        ],
        
        'urlManager' => [
            'enablePrettyUrl' => true,
            'showScriptName' => false,
            'rules' => [
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'api/refeicao',
                    'extraPatterns' => [
                        'GET {date}/{schedule}' => 'getbydateschedule',
                    ],
                    'tokens' => [
                        '{schedule}' => '<schedule:\w+>',
                        '{date}' => '<date:\d{4}-\d{2}-\d{2}>',
                    ]
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'api/reserva',
                    'extraPatterns' => [
                        'GET {init_date}/{end_date}/{id}' => 'getreservationbydates'
                    ],
                    'tokens' => [
                        '{init_date}' => '<init_date:\d{4}-\d{2}-\d{2}>',
                        '{end_date}' => '<end_date:\d{4}-\d{2}-\d{2}>',
                        '{id}' => '<id:\d+>'
                    ]
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'api/quarto',
                    'extraPatterns' => [

                    ],
                    'tokens' => [

                    ]
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'api/servico',
                    'extraPatterns' => [

                    ],
                    'tokens' => [

                    ]
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'api/user',
                    'extraPatterns' => [
                        'GET login' => 'login'
                    ],
                    'tokens' => [
                        '{username}' => '<username:[\w\.]++>'
                    ]
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'api/fatura',
                    'extraPatterns' => [

                    ],
                    'tokens' => [

                    ]
                ],
                [
                    'class' => 'yii\rest\UrlRule',
                    'controller' => 'api/linhafatura',
                    'extraPatterns' => [

                    ],
                    'tokens' => [

                    ]
                ],
            ],
        ],
        // 'view' => [
        //         'theme' => [
        //             'pathMap' => [
        //             '@app/views' => '@vendor/hail812/yii2-adminlte3/src/views'
        //             ],
        //         ],
        // ],
    ],
    'params' => $params,
];
