<template lang="">
    <div>
        <section id="hero">
            <h1>Welcomne</h1>
            <p>
                迎着盛大的夕阳去逃亡吧！
            </p>
        </section>
    </div>
</template>
<script>
    export default {
        name: 'home'
    }
</script>
<style scoped>
    section#hero {
        height: calc(100vh - 60px);
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        text-align: center;
        color: #5a728f;
    }

    #hero>h1 {
        font-size: 3rem;
        margin-bottom: 1rem;
    }
</style>