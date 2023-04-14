const functions = require("firebase-functions");
const admin = require("firebase-admin");

admin.initializeApp();

exports.updateField = functions.region("southamerica-east1").pubsub.schedule("59 2 * * *").timeZone("UTC").onRun(async (context) => {
  const collectionRef = admin.firestore().collection("habits");
  const querySnapshot = await collectionRef.where("completed", "==", true).get();
  const batch = admin.firestore().batch();
  querySnapshot.forEach((doc) => {
    const docRef = collectionRef.doc(doc.id);
    batch.update(docRef, {completed: false});
  });
  await batch.commit();
});

exports.sendNotifications = functions.pubsub.schedule("every 5 minutes").onRun(async (context) => {
  const habits = await admin.firestore().collection("habits").get();
  const now = new Date();

  habits.forEach((habit) => {
    const habitData = habit.data();
    const habitTime = new Date(habitData.time);

    if (habitData.days.includes(now.getDay()) && habitTime.getHours() === now.getHours() && habitTime.getMinutes() === now.getMinutes()) {
      admin.messaging().send({
        notification: {
          title: `${habitData.username}!`,
          body: `Es hora de realizar el hábito: ${habitData.habitName}`,
        },
        token: habitData.fcmToken,
      });
    }
  });
});
