<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "reserva".
 *
 * @property int $id
 * @property int $quarto_id
 * @property int $cliente_id
 * @property string $data_inicial
 * @property string $data_final
 * @property float $preco_total
 *
 * @property User $cliente
 * @property LinhaFatura[] $linhaFaturas
 * @property Quarto $quarto
 */
class Reserva extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'reserva';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['quarto_id', 'cliente_id', 'data_inicial', 'data_final', 'preco_total'], 'required'],
            [['quarto_id', 'cliente_id'], 'integer'],
            [['data_inicial', 'data_final'], 'safe'],
            [['preco_total'], 'number'],
            [['quarto_id'], 'exist', 'skipOnError' => true, 'targetClass' => Quarto::class, 'targetAttribute' => ['quarto_id' => 'id']],
            [['cliente_id'], 'exist', 'skipOnError' => true, 'targetClass' => User::class, 'targetAttribute' => ['cliente_id' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'quarto_id' => 'Quarto ID',
            'cliente_id' => 'Cliente ID',
            'data_inicial' => 'Data Inicial',
            'data_final' => 'Data Final',
            'preco_total' => 'Preco Total',
        ];
    }

    /**
     * Gets query for [[Cliente]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getCliente()
    {
        return $this->hasOne(User::class, ['id' => 'cliente_id']);
    }

    /**
     * Gets query for [[LinhaFaturas]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getLinhaFaturas()
    {
        return $this->hasMany(LinhaFatura::class, ['reserva_id' => 'id']);
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
