public class playerStats {
        private int speed, strength, health, XP, level, lvlNext, modifier, statusEffectChance, statusEffectDamage, Ability4Damage;
        private double Armor;

        public playerStats(){
            this.speed = 30;
            this.strength = 30;
            this.health = 100;
            this.level = 1;
            this.Armor = 1;
            this.XP = 0;
            this.lvlNext = 25;
        }

        //Getters and setters
        public int getSpeed() {
            return speed;
        }

        public int getStrength() {
            return strength;
        }

        public int getHealth() {
            return health;
        }

        public int getLevel() {
            return level;
        }

        public int getXP() {
            return XP;
        }

        public int getLvlNext() {
            return lvlNext;
        }

        public double getArmor(){ return Armor;}

        public int getModifier() {
            int random = (int) (Math.random() * 5 + -5);
            //System.out.println(random);
            return random;
        }

        public int getStatusEffectChance() {
            int firstRandom = (int) (Math.random() * 4 + 1);
            int secondRandom = (int) (Math.random() * 4 + 1);
            if(firstRandom == secondRandom){
                return 1;
            }else {
                return 0;
            }
        }

        public int getStatusEffectDamage() {
            int statusEffectDamage = (int) (Math.random()* 10 + 6);
            return statusEffectDamage;
        }

        public int getAbility1Damage() {
            return ((int) ((getLevel() + getStrength()) * 1.00) + getModifier());
        }

        public int getAbility2Damage() {
            return ((int) ((getLevel() + getStrength()) * 0.80) + getModifier());
        }

        public int getAbility3Damage() {
            return (int) ((getLevel() + getStrength()) * 1.30) + getModifier();
        }

        public int getAbility4Damage() {
            return (int) ((getLevel() + getStrength()) * 1.30) + getModifier();
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public void setArmor(double armor) {
            Armor = armor;
        }

        public void setStrength(int strength) {
            this.strength = strength;
        }

        public void setHealth(int health) {
            this.health = health;
        }

        public void setStatusEffectDamage(int statusEffectDamage) {
            this.statusEffectDamage = statusEffectDamage;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public void setXP(int XP) {
            this.XP = XP;
        }

        public void setLvlNext(int lvlNext) {
            this.lvlNext = lvlNext;
        }
    }
