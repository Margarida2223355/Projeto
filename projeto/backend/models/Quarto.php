<?php

namespace backend\models;

use Yii;

/**
 * This is the model class for table "quarto".
 *
 * @property int $id
 * @property string $descricao
 * @property float $preco
 */
class Quarto extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'quarto';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['descricao', 'preco'], 'required'],
            [['preco'], 'number'],
            [['descricao'], 'string', 'max' => 50],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'descricao' => 'Descricao',
            'preco' => 'Preco',
        ];
    }
}
