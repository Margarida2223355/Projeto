<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "linha_fatura_has_fatura".
 *
 * @property int $linha_fatura_id
 * @property int $fatura_id
 *
 * @property Fatura $fatura
 * @property LinhaFatura $linhaFatura
 */
class LinhaFaturaHasFatura extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'linha_fatura_has_fatura';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['linha_fatura_id', 'fatura_id'], 'required'],
            [['linha_fatura_id', 'fatura_id'], 'integer'],
            [['linha_fatura_id', 'fatura_id'], 'unique', 'targetAttribute' => ['linha_fatura_id', 'fatura_id']],
            [['fatura_id'], 'exist', 'skipOnError' => true, 'targetClass' => Fatura::class, 'targetAttribute' => ['fatura_id' => 'id']],
            [['linha_fatura_id'], 'exist', 'skipOnError' => true, 'targetClass' => LinhaFatura::class, 'targetAttribute' => ['linha_fatura_id' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'linha_fatura_id' => 'Linha Fatura ID',
            'fatura_id' => 'Fatura ID',
        ];
    }

    /**
     * Gets query for [[Fatura]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getFatura()
    {
        return $this->hasOne(Fatura::class, ['id' => 'fatura_id']);
    }

    /**
     * Gets query for [[LinhaFatura]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getLinhaFatura()
    {
        return $this->hasOne(LinhaFatura::class, ['id' => 'linha_fatura_id']);
    }
}
