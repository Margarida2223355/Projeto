<?php

namespace common\models;

use common\models\Quarto;
use Yii;

/**
 * This is the model class for table "img".
 *
 * @property int $id
 * @property string $descricao
 * @property int $quarto_id 
 *
 * @property ImgQuartoRelacionamento[] $imgQuartoRelacionamentos
 * @property Quarto $quarto
 */
class Img extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'img';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['image', 'descricao', 'quarto_id'], 'required'],
            [['quarto_id'], 'integer'],
            [['image'], 'file', 'extensions'=>'png,jpg,gif','maxFiles'=>5,'skipOnEmpty'=>false],
            [['descricao'], 'string', 'max' => 100],
            [['quarto_id'], 'exist', 'skipOnError' => true, 'targetClass' => Quarto::class, 'targetAttribute' => ['quarto_id' => 'id']], 
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'image' => 'Image',
            'descricao' => 'Descricao',
            'quarto_id' => 'Quarto ID', 
        ];
    }

    /** 
    * Gets query for [[Quarto]]. 
    * 
    * @return \yii\db\ActiveQuery 
    */ 
   public function getQuarto() 
   { 
       return $this->hasOne(Quarto::class, ['id' => 'quarto_id']); 
   } 
}
