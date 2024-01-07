<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "fatura".
 *
 * @property int $id
 * @property string|null $data_pagamento
 * @property float|null $preco_total
 * @property int $reserva_id
 * @property int $pousada_id
 *
 * @property LinhaFaturaHasFatura[] $linhaFaturaHasFaturas
 * @property LinhaFatura[] $linhaFaturas
 * @property Pousada $pousada
 * @property Reserva $reserva
 */
class Fatura extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'fatura';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['reserva_id', 'pousada_id'], 'required'],
            [['reserva_id', 'pousada_id'], 'integer'],
            [['data_pagamento'], 'safe'],
            [['preco_total'], 'number'],
            [['id'], 'unique'],
            [['pousada_id'], 'exist', 'skipOnError' => true, 'targetClass' => Pousada::class, 'targetAttribute' => ['pousada_id' => 'id']],
            [['reserva_id'], 'exist', 'skipOnError' => true, 'targetClass' => Reserva::class, 'targetAttribute' => ['reserva_id' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'data_pagamento' => 'Data Pagamento',
            'preco_total' => 'Preco Total',
            'reserva_id' => 'Reserva ID',
            'pousada_id' => 'Pousada ID',
        ];
    }


    /**
     * Gets query for [[LinhaFaturas]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getLinhaFaturas()
    {
        return $this->hasMany(LinhaFatura::class, ['id' => 'linha_fatura_id'])->viaTable('linha_fatura_has_fatura', ['fatura_id' => 'id']);
    }

    /**
     * Gets query for [[Pousada]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getPousada()
    {
        return $this->hasOne(Pousada::class, ['id' => 'pousada_id']);
    }

    /**
     * Gets query for [[Reserva]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getReserva()
    {
        return $this->hasOne(Reserva::class, ['id' => 'reserva_id']);
    }
    
    public static function faturaExistsForReservaId($reservaId)
    {
        return static::find()->where(['reserva_id' => $reservaId])->exists();
    }
    public static function getTotalFaturas(){
        return static::find()->count();
    }
}
