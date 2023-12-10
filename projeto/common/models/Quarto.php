<?php

namespace common\models;

use common\models\Img;
use Yii;

/**
 * This is the model class for table "quarto".
 *
 * @property int $id
 * @property string $descricao
 * @property int $camas_solteiro
 * @property int $camas_casal
 * @property int $arcondicionado
 * @property int $aquecedor
 * @property float $preco
 *
 * @property Reserva[] $reservas
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
            [['descricao', 'camas_solteiro', 'camas_casal', 'arcondicionado', 'aquecedor', 'preco'], 'required'],
            [['camas_solteiro', 'camas_casal', 'arcondicionado', 'aquecedor'], 'integer'],
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
            'camas_solteiro' => 'Camas Solteiro',
            'camas_casal' => 'Camas Casal',
            'arcondicionado' => 'Arcondicionado',
            'aquecedor' => 'Aquecedor',
            'preco' => 'Preco',
        ];
    }

    /**
     * Gets query for [[Reservas]].
     *
     * @return \yii\db\ActiveQuery
     */
    public function getReservas()
    {
        return $this->hasMany(Reserva::class, ['quarto_id' => 'id']);
    }

    public function getImgs()
    {
        return $this->hasMany(Img::class, ['quarto_id' => 'id']);
    }

    public function getArcondicionado()
    {
        return $this->arcondicionado ? 'Disponivel' : 'Indisponivel';
    }

    public function getAquecedor()
    {
        return $this->aquecedor ? 'Disponivel' : 'Indisponivel';
    }
}
