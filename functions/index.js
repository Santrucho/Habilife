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
